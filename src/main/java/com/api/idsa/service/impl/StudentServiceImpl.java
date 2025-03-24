package com.api.idsa.service.impl;

import com.api.idsa.dto.request.StudentRequest;
import com.api.idsa.dto.response.StudentResponse;
import com.api.idsa.exception.DuplicateResourceException;
import com.api.idsa.exception.ResourceNotFoundException;
import com.api.idsa.mapper.IStudentMapper;
import com.api.idsa.model.GroupConfigurationEntity;
import com.api.idsa.model.PersonEntity;
import com.api.idsa.model.StudentEntity;
import com.api.idsa.repository.IGroupConfigurationRepository;
import com.api.idsa.repository.IPersonRepository;
import com.api.idsa.repository.IStudentRepository;
import com.api.idsa.repository.ITutorRepository;
import com.api.idsa.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    IStudentRepository studentRepository;

    @Autowired
    ITutorRepository tutorRepository;

    @Autowired
    IPersonRepository personRepository;

    @Autowired
    IGroupConfigurationRepository groupConfigurationRepository;

    @Autowired
    IStudentMapper studentMapper;

    @Override
    public List<StudentResponse> findAll() {
        return studentMapper.toResponseList(studentRepository.findAll());
    }

    @Override
    public List<StudentResponse> findByGroupConfigurationId(Long groupConfigurationId) {
        return studentMapper.toResponseList(studentRepository.findByGroupConfiguration_GroupConfigurationId(groupConfigurationId));
    }

    @Override
    public StudentResponse createStudent(StudentRequest studentRequest, Long groupConfigurationId) throws DuplicateResourceException, ResourceNotFoundException {

        if (studentRepository.existsByStudentCode(studentRequest.getStudentCode())) {
            throw new DuplicateResourceException("Create", "Student", "StudentCode", studentRequest.getStudentCode());
        } else if (tutorRepository.existsByEmployeeCode(studentRequest.getStudentCode())) {
            throw new DuplicateResourceException("Create", "Student", "StudentCode_EmployeeCode", studentRequest.getStudentCode());
        } else if (studentRepository.existsByPerson_PhoneNumber(studentRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("Create", "Student", "PhoneNumber", studentRequest.getPhoneNumber());
        }

        GroupConfigurationEntity groupConfiguration = groupConfigurationRepository.findById(groupConfigurationId)
                .orElseThrow(() -> new ResourceNotFoundException("Create Student", "GroupConfiguration", groupConfigurationId));

        PersonEntity personEntity = studentMapper.toPersonEntity(studentRequest);
        personRepository.save(personEntity);

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setPerson(personEntity);
        studentEntity.setStudentCode(studentRequest.getStudentCode());
        studentEntity.setGroupConfiguration(groupConfiguration);
        studentRepository.save(studentEntity);

        return studentMapper.toResponse(studentEntity);
    }

    @Override
    public StudentResponse updateStudent(Long studentId, StudentRequest studentRequest) throws DuplicateResourceException, ResourceNotFoundException {
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Update", "Student", studentId));

        // Validar duplicados de studentCode (excepto si es el mismo estudiante)
        if (!studentEntity.getStudentCode().equals(studentRequest.getStudentCode()) && studentRepository.existsByStudentCode(studentRequest.getStudentCode())) {
            throw new DuplicateResourceException("Update", "Student", "StudentCode", studentRequest.getStudentCode());
        } else if (tutorRepository.existsByEmployeeCode(studentRequest.getStudentCode())) {
            throw new DuplicateResourceException("Update", "Student", "StudentCode_EmployeeCode", studentRequest.getStudentCode());
        } else if (!studentEntity.getPerson().getPhoneNumber().equals(studentRequest.getPhoneNumber()) && studentRepository.existsByPerson_PhoneNumber(studentRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("Update", "Student", "PhoneNumber", studentRequest.getPhoneNumber());
        }

        // Actualizar datos de la persona
        PersonEntity personEntity = studentEntity.getPerson();
        personEntity.setName(studentRequest.getName());
        personEntity.setFirstSurname(studentRequest.getFirstSurname());
        personEntity.setSecondSurname(studentRequest.getSecondSurname());
        personEntity.setPhoneNumber(studentRequest.getPhoneNumber());
        personRepository.save(personEntity);

        // Actualizar datos del estudiante
        studentEntity.setPerson(personEntity);
        studentEntity.setStudentCode(studentRequest.getStudentCode());
        studentRepository.save(studentEntity);

        return studentMapper.toResponse(studentEntity);
    }

    @Override
    public void deleteStudent(Long studentId) throws ResourceNotFoundException {
        StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Delete", "Student", studentId));
        studentRepository.delete(studentEntity);
    }
}
