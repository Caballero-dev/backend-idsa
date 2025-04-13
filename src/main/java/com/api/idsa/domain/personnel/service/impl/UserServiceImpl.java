package com.api.idsa.domain.personnel.service.impl;

import com.api.idsa.common.exception.DuplicateResourceException;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IPersonRepository personRepository;

    @Autowired
    ITutorRepository tutorRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IUserMapper userMapper;

    @Override
    public Page<UserResponse> getAllUser(Pageable pageable) {
        Page<UserEntity> userPage = userRepository.findAll(pageable);
        return userPage.map(userMapper::toResponse);
    }

    @Override
    public Page<UserResponse> getAllActiveExceptAdmin(Pageable pageable) {
        Page<UserEntity> userPage = userRepository.findAllByIsActiveTrueAndRole_RoleNameIsNot("ROLE_ADMIN", pageable);
        return userPage.map(userMapper::toResponse);
    }

    @Override
    public Page<UserResponse> getAllInactiveExceptAdmin(Pageable pageable) {
        Page<UserEntity> userPage = userRepository.findAllByIsActiveFalseAndRole_RoleNameIsNot("ROLE_ADMIN", pageable);
        return userPage.map(userMapper::toResponse);
    }

    @Transactional
    @Override
    public UserResponse createUser(UserRequest userRequest) {

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new DuplicateResourceException("create", "User", "email", userRequest.getEmail());
        }
        if (personRepository.existsByPhoneNumber(userRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("create", "User", "phoneNumber", userRequest.getPhoneNumber());
        }
        if (personRepository.existsByTutor_EmployeeCode(userRequest.getKey())) {
            throw new DuplicateResourceException("create", "User", "key_employeeCode", userRequest.getKey());
        }
        if (personRepository.existsByStudent_StudentCode(userRequest.getKey())) {
            throw new DuplicateResourceException("create", "User", "key_studentCode", userRequest.getKey());
        }

        RoleEntity roleEntity = roleRepository.findByRoleName(userRequest.getRole().getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("create", "User", "RoleName", userRequest.getRole().getRoleId()));

        if ("ROLE_ADMIN".equals(roleEntity.getRoleName())) throw new UserRoleCreationDeniedException();

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
        userEntity.setPassword(userRequest.getPassword());
        userEntity.setCreatedAt(ZonedDateTime.now());
        userEntity.setIsActive(false);
        userEntity.setIsVerifiedEmail(false);
        userRepository.save(userEntity);
        // TODO: implementar logica de envio de correo de verificacion y colocar contraseña

        return userMapper.toResponse(userEntity);
    }

    @Transactional
    @Override
    public UserResponse updateUser(Long userId, boolean isUpdatePassword, UserRequest updateUserRequest) {

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("update", "User", userId));

        if (!userEntity.getEmail().equals(updateUserRequest.getEmail()) && userRepository.existsByEmail(updateUserRequest.getEmail())) {
            throw new DuplicateResourceException("update", "User", "email", updateUserRequest.getEmail());
        }
        if (!userEntity.getPerson().getPhoneNumber().equals(updateUserRequest.getPhoneNumber()) && personRepository.existsByPhoneNumber(updateUserRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("update", "User", "phoneNumber", updateUserRequest.getPhoneNumber());
        }
        if (
            userEntity.getPerson().getTutor() != null &&
            !userEntity.getPerson().getTutor().getEmployeeCode().equals(updateUserRequest.getKey()) &&
            personRepository.existsByTutor_EmployeeCode(updateUserRequest.getKey())
            ) {
            throw new DuplicateResourceException("update", "User", "key_employeeCode", updateUserRequest.getKey());
        }
        if (personRepository.existsByStudent_StudentCode(updateUserRequest.getKey())) {
            throw new DuplicateResourceException("update", "User", "key_studentCode", updateUserRequest.getKey());
        }

        if ("ROLE_ADMIN".equals(updateUserRequest.getRole().getRoleId())) throw new UserRoleCreationDeniedException("Update of user with the role 'ADMIN' is denied");

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
            // TODO: implementar logica verificación del nuevo correo
        }
        if (isUpdatePassword) userEntity.setPassword(updateUserRequest.getPassword());

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
        // FIXME: Si tiene grupos asignados se decir que no se puede elimininar por que tiene grupos asignados, que en confiuguración grupo cambie el tutor por otro
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("delete", "User", userId));

        userRepository.delete(userEntity);
    }
    
}
