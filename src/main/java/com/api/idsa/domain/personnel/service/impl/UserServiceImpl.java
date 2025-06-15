package com.api.idsa.domain.personnel.service.impl;

import com.api.idsa.common.exception.DuplicateResourceException;
import com.api.idsa.common.exception.ResourceDependencyException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.common.exception.UserRoleCreationDeniedException;
import com.api.idsa.domain.personnel.dto.request.UserRequest;
import com.api.idsa.domain.personnel.dto.response.UserResponse;
import com.api.idsa.domain.personnel.mapper.IUserMapper;
import com.api.idsa.domain.personnel.model.PersonEntity;
import com.api.idsa.domain.personnel.model.RoleEntity;
import com.api.idsa.domain.personnel.model.TutorEntity;
import com.api.idsa.domain.personnel.model.UserEntity;
import com.api.idsa.domain.personnel.repository.IPersonRepository;
import com.api.idsa.domain.personnel.repository.IRoleRepository;
import com.api.idsa.domain.personnel.repository.ITutorRepository;
import com.api.idsa.domain.personnel.repository.IUserRepository;
import com.api.idsa.domain.personnel.service.IUserService;
import com.api.idsa.infrastructure.mail.service.MailService;
import com.api.idsa.security.enums.TokenType;
import com.api.idsa.security.provider.EmailTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IPersonRepository personRepository;

    @Autowired
    private ITutorRepository tutorRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IUserMapper userMapper;

    @Autowired
    private EmailTokenProvider emailTokenProvider;

    @Autowired
    private MailService mailService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<UserResponse> getAllUserExceptAdmin(Pageable pageable) {
        Page<UserEntity> userPage = userRepository.findAllByRole_RoleNameIsNot("ROLE_ADMIN", pageable);
        return userPage.map(userMapper::toResponse);
    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new DuplicateResourceException("User", "email", userRequest.getEmail());
        }
        if (personRepository.existsByPhoneNumber(userRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("User", "phone_number", userRequest.getPhoneNumber());
        }
        if (personRepository.existsByTutor_EmployeeCode(userRequest.getKey())) {
            throw new DuplicateResourceException("User", "key_employee_code", userRequest.getKey());
        }
        if (personRepository.existsByStudent_StudentCode(userRequest.getKey())) {
            throw new DuplicateResourceException("User", "key_student_code", userRequest.getKey());
        }

        RoleEntity roleEntity = roleRepository.findByRoleName(userRequest.getRole().getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role", "role_name", userRequest.getRole().getRoleId()));

        if ("ROLE_ADMIN".equals(roleEntity.getRoleName())) throw new UserRoleCreationDeniedException("Creation of user with the specified role is denied");

        PersonEntity personEntity = userMapper.toPersonEntity(userRequest);
        personRepository.save(personEntity);

        if (userRequest.getRole().getRoleId().equals("ROLE_TUTOR")) {
            TutorEntity tutorEntity = new TutorEntity();
            tutorEntity.setPerson(personEntity);
            tutorEntity.setEmployeeCode(userRequest.getKey());
            tutorEntity = tutorRepository.save(tutorEntity);
            personEntity.setTutor(tutorEntity);
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setPerson(personEntity);
        userEntity.setRole(roleEntity);
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setCreatedAt(ZonedDateTime.now());
        userEntity.setIsActive(false);
        userEntity.setIsVerifiedEmail(false);
        userRepository.save(userEntity);

        String token = emailTokenProvider.generateVerificationToken(userEntity.getEmail(), TokenType.EMAIL_VERIFICATION);
        mailService.sendVerificationEmail(userEntity.getEmail(), token);

        return userMapper.toResponse(userEntity);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long userId, boolean isUpdatePassword, UserRequest updateUserRequest) {

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "user_id", userId));

        if (!userEntity.getEmail().equals(updateUserRequest.getEmail()) && userRepository.existsByEmail(updateUserRequest.getEmail())) {
            throw new DuplicateResourceException("User", "email", updateUserRequest.getEmail());
        }
        if (!userEntity.getPerson().getPhoneNumber().equals(updateUserRequest.getPhoneNumber()) && personRepository.existsByPhoneNumber(updateUserRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("User", "phone_number", updateUserRequest.getPhoneNumber());
        }
        if (
            userEntity.getPerson().getTutor() != null &&
            !userEntity.getPerson().getTutor().getEmployeeCode().equals(updateUserRequest.getKey()) &&
            personRepository.existsByTutor_EmployeeCode(updateUserRequest.getKey())
        ) {
            throw new DuplicateResourceException("User", "key_employee_code", updateUserRequest.getKey());
        }
        if (personRepository.existsByStudent_StudentCode(updateUserRequest.getKey())) {
            throw new DuplicateResourceException("User", "key_student_code", updateUserRequest.getKey());
        }

        if ("ROLE_ADMIN".equals(updateUserRequest.getRole().getRoleId())) throw new UserRoleCreationDeniedException("Update of user with the specified role is denied");

        PersonEntity person = userEntity.getPerson();
        person.setName(updateUserRequest.getName());
        person.setFirstSurname(updateUserRequest.getFirstSurname());
        person.setSecondSurname(updateUserRequest.getSecondSurname());
        person.setPhoneNumber(updateUserRequest.getPhoneNumber());
        personRepository.save(person);

        if (updateUserRequest.getRole().getRoleId().equals("ROLE_TUTOR")) {
            TutorEntity tutor = userEntity.getPerson().getTutor();
            tutor.setPerson(person);
            tutor.setEmployeeCode(updateUserRequest.getKey());
            tutorRepository.save(tutor);
            person.setTutor(tutor);
        }

        if (!userEntity.getEmail().equals(updateUserRequest.getEmail())){
            userEntity.setIsActive(false);
            userEntity.setIsVerifiedEmail(false);
            userEntity.setEmail(updateUserRequest.getEmail());
            
            String token = emailTokenProvider.generateVerificationToken(updateUserRequest.getEmail(), TokenType.EMAIL_CHANGE);
            mailService.sendEmailChangeConfirmation(updateUserRequest.getEmail(), token);
        }
        if (isUpdatePassword) userEntity.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));

        userRepository.save(userEntity);

        return userMapper.toResponse(userEntity);
    }

    @Override
	public void updateUserStatus(Long userId, boolean isActive) {

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("update", "User", userId));

        userEntity.setIsActive(isActive);
        userRepository.save(userEntity);
	}

    @Override
    public void deleteUser(Long userId) {
        try {
            UserEntity userEntity = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("delete", "User", userId));

            userRepository.delete(userEntity);            
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("group_configurations_tutor_id_fkey")) {
                throw new ResourceDependencyException("User", userId, "groups assigned as tutor");
            }
            throw new ResourceDependencyException("User", userId, " associated records");
        }

    }
    
}
