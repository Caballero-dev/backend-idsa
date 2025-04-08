package com.api.idsa.domain.personnel.service.impl;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.personnel.dto.request.TutorRequest;
import com.api.idsa.domain.personnel.dto.response.TutorResponse;
import com.api.idsa.domain.personnel.mapper.ITutorMapper;
import com.api.idsa.domain.personnel.model.PersonEntity;
import com.api.idsa.domain.personnel.model.RoleEntity;
import com.api.idsa.domain.personnel.model.TutorEntity;
import com.api.idsa.domain.personnel.model.UserEntity;
import com.api.idsa.domain.personnel.repository.IPersonRepository;
import com.api.idsa.domain.personnel.repository.IRoleRepository;
import com.api.idsa.domain.personnel.repository.IStudentRepository;
import com.api.idsa.domain.personnel.repository.ITutorRepository;
import com.api.idsa.domain.personnel.repository.IUserRepository;
import com.api.idsa.domain.personnel.service.ITutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class TutorServiceImpl implements ITutorService {

    @Autowired
    ITutorRepository tutorRepository;

    @Autowired
    IPersonRepository personRepository;

    @Autowired
    IStudentRepository studentRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ITutorMapper tutorMapper;

    @Override
    public List<TutorResponse> findAll() {
        return tutorMapper.toResponseList(tutorRepository.findAll());
    }

    @Transactional
    @Override
    public TutorResponse createTutor(TutorRequest tutorRequest) throws DuplicateResourceException, ResourceNotFoundException {

        if (tutorRepository.existsByEmployeeCode(tutorRequest.getEmployeeCode())) {
            throw new DuplicateResourceException("create", "Tutor", "EmployeeCode", tutorRequest.getEmployeeCode());
        } else if (studentRepository.existsByStudentCode(tutorRequest.getEmployeeCode())) {
            throw new DuplicateResourceException("create", "Student", "StudentCode", tutorRequest.getEmployeeCode());
        } else if (personRepository.existsByPhoneNumber(tutorRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("create", "Person", "PhoneNumber", tutorRequest.getPhoneNumber());
        } else if (userRepository.existsByEmail(tutorRequest.getEmail())) {
            throw new DuplicateResourceException("create", "User", "Email", tutorRequest.getEmail());
        }

        RoleEntity roleEntity = roleRepository.findByRoleName("ROLE_TUTOR")
                .orElseThrow(() -> new ResourceNotFoundException("create", "Role", "RoleName", "ROLE_TUTOR"));

        PersonEntity personEntity = tutorMapper.toPersonEntity(tutorRequest);
        personRepository.save(personEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setPerson(personEntity);
        userEntity.setRole(roleEntity);
        userEntity.setEmail(tutorRequest.getEmail());
        userEntity.setPassword(tutorRequest.getPassword());
        userEntity.setCreatedAt(ZonedDateTime.now());
        userEntity.setIsActive(true);
        userRepository.save(userEntity);
        personEntity.setUser(userEntity);

        TutorEntity tutorEntity = new TutorEntity();
        tutorEntity.setPerson(personEntity);
        tutorEntity.setEmployeeCode(tutorRequest.getEmployeeCode());
        tutorRepository.save(tutorEntity);

        return tutorMapper.toResponse(tutorEntity);
    }

    @Transactional
    @Override
    public TutorResponse updateTutor(Long tutorId, TutorRequest tutorRequest) throws ResourceNotFoundException, DuplicateResourceException {
        TutorEntity tutorEntity = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new ResourceNotFoundException("update", "Tutor", tutorId));

        if (!tutorEntity.getEmployeeCode().equals(tutorRequest.getEmployeeCode()) && tutorRepository.existsByEmployeeCode(tutorRequest.getEmployeeCode())) {
            throw new DuplicateResourceException("update", "Tutor", "EmployeeCode", tutorRequest.getEmployeeCode());
        } else if (!tutorEntity.getPerson().getPhoneNumber().equals(tutorRequest.getPhoneNumber()) && personRepository.existsByPhoneNumber(tutorRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("update", "Tutor", "PhoneNumber", tutorRequest.getPhoneNumber());
        } else if (!tutorEntity.getPerson().getUser().getEmail().equals(tutorRequest.getEmail()) && userRepository.existsByEmail(tutorRequest.getEmail())) {
            throw new DuplicateResourceException("update", "Tutor", "Email", tutorRequest.getEmail());
        } else if (studentRepository.existsByStudentCode(tutorRequest.getEmployeeCode())) {
            throw new DuplicateResourceException("update", "Tutor", "StudentCode", tutorRequest.getEmployeeCode());
        }

        PersonEntity person = tutorEntity.getPerson();
        person.setName(tutorRequest.getName());
        person.setFirstSurname(tutorRequest.getFirstSurname());
        person.setSecondSurname(tutorRequest.getSecondSurname());
        person.setPhoneNumber(tutorRequest.getPhoneNumber());
        personRepository.save(person);

        UserEntity user = tutorEntity.getPerson().getUser();
        user.setPerson(person);
        user.setEmail(tutorRequest.getEmail());
        userRepository.save(user);
        person.setUser(user);

        tutorEntity.setPerson(person);
        tutorEntity.setEmployeeCode(tutorRequest.getEmployeeCode());
        tutorRepository.save(tutorEntity);

        return tutorMapper.toResponse(tutorEntity);
    }

    @Override
    public void deleteTutor(Long tutorId) throws ResourceNotFoundException {
        TutorEntity tutorEntity = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new ResourceNotFoundException("delete", "Tutor", tutorId));
        tutorRepository.delete(tutorEntity);
    }

}
