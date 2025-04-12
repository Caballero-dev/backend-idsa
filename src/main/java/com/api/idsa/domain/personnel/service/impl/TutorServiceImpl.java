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
    IPersonRepository personRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ITutorRepository tutorRepository;

    @Autowired
    IStudentRepository studentRepository;

    @Autowired
    ITutorMapper tutorMapper;

    @Override
    public List<TutorResponse> getAllTutor() {
        return tutorMapper.toResponseList(tutorRepository.findAll());
    }

    @Transactional
    @Override
    public TutorResponse createTutor(TutorRequest tutorRequest) {

        if (tutorRepository.existsByEmployeeCode(tutorRequest.getEmployeeCode())) {
            throw new DuplicateResourceException("create", "Tutor", "employeeCode", tutorRequest.getEmployeeCode());
        }
        // personRepository.existsByTutor_EmployeeCode(tutorRequest.getEmployeeCode());
        if (studentRepository.existsByStudentCode(tutorRequest.getEmployeeCode())) {
            throw new DuplicateResourceException("create", "Tutor", "employeeCode", tutorRequest.getEmployeeCode());
        } 
        if (personRepository.existsByPhoneNumber(tutorRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("create", "Tutor", "phoneNumber", tutorRequest.getPhoneNumber());
        }
        if (userRepository.existsByEmail(tutorRequest.getEmail())) {
            throw new DuplicateResourceException("create", "Tutor", "email", tutorRequest.getEmail());
        }

        RoleEntity roleEntity = roleRepository.findByRoleName("ROLE_TUTOR")
                .orElseThrow(() -> new ResourceNotFoundException("create", "Tutor", "RoleName", "ROLE_TUTOR"));

        PersonEntity personEntity = tutorMapper.toPersonEntity(tutorRequest);
        personRepository.save(personEntity);

        UserEntity userEntity = new UserEntity();
        userEntity.setPerson(personEntity);
        userEntity.setRole(roleEntity);
        userEntity.setEmail(tutorRequest.getEmail());
        userEntity.setCreatedAt(ZonedDateTime.now());
        userEntity.setIsActive(false);
        userEntity.setIsVerifiedEmail(false);
        // TODO: implementar el envio de correo de verificacion y set de contraseña
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
    public TutorResponse updateTutor(Long tutorId, TutorRequest tutorRequest) {

        TutorEntity tutorEntity = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new ResourceNotFoundException("update", "Tutor", tutorId));

        if (!tutorEntity.getEmployeeCode().equals(tutorRequest.getEmployeeCode()) && tutorRepository.existsByEmployeeCode(tutorRequest.getEmployeeCode())) {
            throw new DuplicateResourceException("update", "Tutor", "employeeCode", tutorRequest.getEmployeeCode());
        }
        // personRepository.existsByTutor_EmployeeCode(tutorRequest.getEmployeeCode());
        if (studentRepository.existsByStudentCode(tutorRequest.getEmployeeCode())) {
            throw new DuplicateResourceException("update", "Tutor", "employeeCode", tutorRequest.getEmployeeCode());
        }
        if (!tutorEntity.getPerson().getPhoneNumber().equals(tutorRequest.getPhoneNumber()) && personRepository.existsByPhoneNumber(tutorRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("update", "Tutor", "phoneNumber", tutorRequest.getPhoneNumber());
        }
        if (!tutorEntity.getPerson().getUser().getEmail().equals(tutorRequest.getEmail()) && userRepository.existsByEmail(tutorRequest.getEmail())) {
            throw new DuplicateResourceException("update", "Tutor", "email", tutorRequest.getEmail());
        }

        // PersonEntity personEntity = tutorMapper.toPersonEntity(tutorRequest);
        // personEntity.setPersonId(tutorEntity.getPerson().getPersonId());

        PersonEntity person = tutorEntity.getPerson();
        person.setName(tutorRequest.getName());
        person.setFirstSurname(tutorRequest.getFirstSurname());
        person.setSecondSurname(tutorRequest.getSecondSurname());
        person.setPhoneNumber(tutorRequest.getPhoneNumber());
        personRepository.save(person);

        // TODO: verificar cuando se actualiza el correo electronico si es diferente al que ya tiene
        // enviar un correo de verificacion y colocae isActive = false, isVerifiedEmail = false
        UserEntity userEntity = tutorEntity.getPerson().getUser();
        userEntity.setPerson(person);
        // user.setEmail(tutorRequest.getEmail());
        if (!userEntity.getEmail().equals(tutorRequest.getEmail())) {
            userEntity.setIsActive(false);
            userEntity.setIsVerifiedEmail(false);
            userEntity.setEmail(tutorRequest.getEmail());
        }
        userRepository.save(userEntity);
        
        person.setUser(userEntity);

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
