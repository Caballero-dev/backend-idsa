package com.api.idsa.domain.personnel.service.impl;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.academic.model.GroupConfigurationEntity;
import com.api.idsa.domain.academic.repository.IGroupConfigurationRepository;
import com.api.idsa.domain.personnel.dto.request.StudentRequest;
import com.api.idsa.domain.personnel.dto.response.StudentResponse;
import com.api.idsa.domain.personnel.mapper.IStudentMapper;
import com.api.idsa.domain.personnel.model.PersonEntity;
import com.api.idsa.domain.personnel.model.StudentEntity;
import com.api.idsa.domain.personnel.repository.IPersonRepository;
import com.api.idsa.domain.personnel.repository.IStudentRepository;
import com.api.idsa.domain.personnel.repository.ITutorRepository;
import com.api.idsa.domain.personnel.service.IStudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    IPersonRepository personRepository;

    @Autowired
    IStudentRepository studentRepository;

    @Autowired
    ITutorRepository tutorRepository;

    @Autowired
    IGroupConfigurationRepository groupConfigurationRepository;

    @Autowired
    IStudentMapper studentMapper;

    @Override
    public List<StudentResponse> getAllStudent() {
        return studentMapper.toResponseList(studentRepository.findAll());
    }

    @Override
    public List<StudentResponse> getStudentsByGroupConfigurationId(Long groupConfigurationId) {
        return studentMapper.toResponseList(studentRepository.findByGroupConfiguration_GroupConfigurationId(groupConfigurationId));
    }

    @Override
    public StudentResponse createStudent(StudentRequest studentRequest, Long groupConfigurationId) {

        if (studentRepository.existsByStudentCode(studentRequest.getStudentCode())) {
            throw new DuplicateResourceException("create", "Student", "studentCode", studentRequest.getStudentCode());
        }
        // personRepository.existsByTutor_EmployeeCode(studentRequest.getStudentCode());
        if (tutorRepository.existsByEmployeeCode(studentRequest.getStudentCode())) {
            throw new DuplicateResourceException("create", "Student", "studentCode", studentRequest.getStudentCode());
        }
        if (studentRepository.existsByPerson_PhoneNumber(studentRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("create", "Student", "phoneNumber", studentRequest.getPhoneNumber());
        }

        GroupConfigurationEntity gcEntity = groupConfigurationRepository.findById(groupConfigurationId)
                .orElseThrow(() -> new ResourceNotFoundException("create", "GroupConfiguration", groupConfigurationId));

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
                .orElseThrow(() -> new ResourceNotFoundException("update", "Student", studentId));

        if (!studentEntity.getStudentCode().equals(studentRequest.getStudentCode()) && studentRepository.existsByStudentCode(studentRequest.getStudentCode())) {
            throw new DuplicateResourceException("update", "Student", "studentCode", studentRequest.getStudentCode());
        }
        // personRepository.existsByTutor_EmployeeCode(studentRequest.getStudentCode());
        if (tutorRepository.existsByEmployeeCode(studentRequest.getStudentCode())) {
            throw new DuplicateResourceException("update", "Student", "studentCode", studentRequest.getStudentCode());
        }
        if (!studentEntity.getPerson().getPhoneNumber().equals(studentRequest.getPhoneNumber()) && studentRepository.existsByPerson_PhoneNumber(studentRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("update", "Student", "phoneNumber", studentRequest.getPhoneNumber());
        }

        // PersonEntity personEntity = studentMapper.toPersonEntity(studentRequest);
        // personEntity.setPersonId(studentEntity.getPerson().getPersonId());

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
    public void deleteStudent(Long studentId) {

        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Delete", "Student", studentId));
        
        studentRepository.delete(studentEntity);
    }
}
