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
import com.api.idsa.domain.personnel.repository.ITutorRepository;
import com.api.idsa.domain.personnel.repository.IUserRepository;
import com.api.idsa.domain.personnel.service.ITutorService;
import com.api.idsa.infrastructure.mail.service.MailService;
import com.api.idsa.security.enums.TokenType;
import com.api.idsa.security.provider.EmailTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class TutorServiceImpl implements ITutorService {

    @Autowired
    private IPersonRepository personRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ITutorRepository tutorRepository;

    @Autowired
    private ITutorMapper tutorMapper;

    @Autowired
    private EmailTokenProvider emailTokenProvider;

    @Autowired
    private MailService mailService;

    @Override
    public Page<TutorResponse> getAllTutor(Pageable pageable) {
        Page<TutorEntity> tutorPage = tutorRepository.findAll(pageable);
        return tutorPage.map(tutorMapper::toResponse);
    }

    @Override
    @Transactional
    public TutorResponse createTutor(TutorRequest tutorRequest) {

        if (tutorRepository.existsByEmployeeCode(tutorRequest.getEmployeeCode())) {
            throw new DuplicateResourceException("create", "Tutor", "employeeCode", tutorRequest.getEmployeeCode());
        }
        if (personRepository.existsByStudent_StudentCode(tutorRequest.getEmployeeCode())) {
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
        userRepository.save(userEntity);

        String token = emailTokenProvider.generateVerificationToken(userEntity.getEmail(), TokenType.EMAIL_VERIFICATION);
        mailService.sendVerificationEmail(userEntity.getEmail(), token);

        personEntity.setUser(userEntity);

        TutorEntity tutorEntity = new TutorEntity();
        tutorEntity.setPerson(personEntity);
        tutorEntity.setEmployeeCode(tutorRequest.getEmployeeCode());
        tutorRepository.save(tutorEntity);

        return tutorMapper.toResponse(tutorEntity);
    }

    @Override
    @Transactional
    public TutorResponse updateTutor(Long tutorId, TutorRequest tutorRequest) {

        TutorEntity tutorEntity = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new ResourceNotFoundException("update", "Tutor", tutorId));

        if (!tutorEntity.getEmployeeCode().equals(tutorRequest.getEmployeeCode()) && tutorRepository.existsByEmployeeCode(tutorRequest.getEmployeeCode())) {
            throw new DuplicateResourceException("update", "Tutor", "employeeCode", tutorRequest.getEmployeeCode());
        }
        if (personRepository.existsByStudent_StudentCode(tutorRequest.getEmployeeCode())) {
            throw new DuplicateResourceException("update", "Tutor", "employeeCode", tutorRequest.getEmployeeCode());
        }
        if (!tutorEntity.getPerson().getPhoneNumber().equals(tutorRequest.getPhoneNumber()) && personRepository.existsByPhoneNumber(tutorRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("update", "Tutor", "phoneNumber", tutorRequest.getPhoneNumber());
        }
        if (!tutorEntity.getPerson().getUser().getEmail().equals(tutorRequest.getEmail()) && userRepository.existsByEmail(tutorRequest.getEmail())) {
            throw new DuplicateResourceException("update", "Tutor", "email", tutorRequest.getEmail());
        }

        PersonEntity person = tutorEntity.getPerson();
        person.setName(tutorRequest.getName());
        person.setFirstSurname(tutorRequest.getFirstSurname());
        person.setSecondSurname(tutorRequest.getSecondSurname());
        person.setPhoneNumber(tutorRequest.getPhoneNumber());
        personRepository.save(person);

        UserEntity userEntity = tutorEntity.getPerson().getUser();
        userEntity.setPerson(person);
        if (!userEntity.getEmail().equals(tutorRequest.getEmail())) {
            userEntity.setIsActive(false);
            userEntity.setIsVerifiedEmail(false);
            userEntity.setEmail(tutorRequest.getEmail());

            String token = emailTokenProvider.generateVerificationToken(tutorRequest.getEmail(), TokenType.EMAIL_CHANGE);
            mailService.sendEmailChangeConfirmation(tutorRequest.getEmail(), token);
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
        // FIXME: Si tiene grupos asignados se decir que no se puede elimininar por que tiene grupos asignados, que en confiuguración grupo cambie el tutor por otro
        TutorEntity tutorEntity = tutorRepository.findById(tutorId)
                .orElseThrow(() -> new ResourceNotFoundException("delete", "Tutor", tutorId));
        
        tutorRepository.delete(tutorEntity);
    }

}
