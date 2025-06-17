package com.api.idsa.domain.personnel.service.impl;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.model.GroupConfigurationEntity;
import com.api.idsa.domain.academic.repository.IGroupConfigurationRepository;
import com.api.idsa.domain.biometric.model.BiometricDataEntity;
import com.api.idsa.domain.personnel.dto.request.StudentRequest;
import com.api.idsa.domain.personnel.dto.response.StudentResponse;
import com.api.idsa.domain.personnel.mapper.IStudentMapper;
import com.api.idsa.domain.personnel.model.PersonEntity;
import com.api.idsa.domain.personnel.model.StudentEntity;
import com.api.idsa.domain.personnel.repository.IPersonRepository;
import com.api.idsa.domain.personnel.repository.IStudentRepository;
import com.api.idsa.domain.personnel.service.IStudentService;
import com.api.idsa.infrastructure.fileStorage.service.IFileStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    IPersonRepository personRepository;

    @Autowired
    IStudentRepository studentRepository;

    @Autowired
    IGroupConfigurationRepository groupConfigurationRepository;

    @Autowired
    IStudentMapper studentMapper;
    
    @Autowired
    IFileStorageService fileStorageService;

    @Override
    public Page<StudentResponse> getAllStudent(Pageable pageable) {
        Page<StudentEntity> studentPage = studentRepository.findAll(pageable);
        return studentPage.map(studentMapper::toResponse);
    }

    @Override
    public Page<StudentResponse> getStudentsByGroupConfigurationId(Long groupConfigurationId, Pageable pageable) {
        Page<StudentEntity> studentPage = studentRepository.findByGroupConfiguration_GroupConfigurationId(groupConfigurationId, pageable);
        return studentPage.map(studentMapper::toResponse);
    }

    @Override
    public StudentResponse createStudent(StudentRequest studentRequest, Long groupConfigurationId) {

        if (studentRepository.existsByStudentCode(studentRequest.getStudentCode())) {
            throw new DuplicateResourceException("Student", "student_code", studentRequest.getStudentCode());
        }
        if (personRepository.existsByTutor_EmployeeCode(studentRequest.getStudentCode())) {
            throw new DuplicateResourceException("Student", "student_code", studentRequest.getStudentCode());
        }
        if (personRepository.existsByPhoneNumber(studentRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("Student", "phone_number", studentRequest.getPhoneNumber());
        }

        GroupConfigurationEntity gcEntity = groupConfigurationRepository.findById(groupConfigurationId)
                .orElseThrow(() -> new ResourceNotFoundException("Group Configuration", "id", groupConfigurationId));

        PersonEntity personEntity = studentMapper.toPersonEntity(studentRequest);
        personRepository.save(personEntity);

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setPerson(personEntity);
        studentEntity.setStudentCode(studentRequest.getStudentCode());
        studentEntity.setGroupConfiguration(gcEntity);
        studentRepository.save(studentEntity);

        return studentMapper.toResponse(studentEntity);
    }

    @Override
    public StudentResponse updateStudent(Long studentId, StudentRequest studentRequest) {
        
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        if (!studentEntity.getStudentCode().equals(studentRequest.getStudentCode()) && studentRepository.existsByStudentCode(studentRequest.getStudentCode())) {
            throw new DuplicateResourceException("Student", "student_code", studentRequest.getStudentCode());
        }
        if (personRepository.existsByTutor_EmployeeCode(studentRequest.getStudentCode())) {
            throw new DuplicateResourceException("Student", "student_code", studentRequest.getStudentCode());
        }
        if (!studentEntity.getPerson().getPhoneNumber().equals(studentRequest.getPhoneNumber()) && personRepository.existsByPhoneNumber(studentRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("Student", "phone_number", studentRequest.getPhoneNumber());
        }

        PersonEntity personEntity = studentEntity.getPerson();
        personEntity.setName(studentRequest.getName());
        personEntity.setFirstSurname(studentRequest.getFirstSurname());
        personEntity.setSecondSurname(studentRequest.getSecondSurname());
        personEntity.setPhoneNumber(studentRequest.getPhoneNumber());
        personRepository.save(personEntity);

        studentEntity.setPerson(personEntity);
        studentEntity.setStudentCode(studentRequest.getStudentCode());
        studentRepository.save(studentEntity);

        return studentMapper.toResponse(studentEntity);
    }

    @Override
    @Transactional
    public void deleteStudent(Long studentId) {
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
        
        deleteStudentBiometricImages(studentEntity);
        studentRepository.delete(studentEntity);
    }
    
    private void deleteStudentBiometricImages(StudentEntity student) {
        List<BiometricDataEntity> biometricDataList = student.getBiometricData();

        if (biometricDataList == null || biometricDataList.isEmpty()) return;

        for (BiometricDataEntity biometricData : biometricDataList) {
            fileStorageService.deleteFile(biometricData.getImagePath());    
        }

    }
}
