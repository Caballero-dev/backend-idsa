package com.api.idsa.service.impl;

import com.api.idsa.dto.request.UpdateUserRequest;
import com.api.idsa.dto.request.UserRequest;
import com.api.idsa.dto.response.UserResponse;
import com.api.idsa.exception.DuplicateResourceException;
import com.api.idsa.exception.ResourceNotFoundException;
import com.api.idsa.exception.UserRoleCreationDeniedException;
import com.api.idsa.mapper.IUserMapper;
import com.api.idsa.model.PersonEntity;
import com.api.idsa.model.RoleEntity;
import com.api.idsa.model.TutorEntity;
import com.api.idsa.model.UserEntity;
import com.api.idsa.repository.IPersonRepository;
import com.api.idsa.repository.IRoleRepository;
import com.api.idsa.repository.ITutorRepository;
import com.api.idsa.repository.IUserRepository;
import com.api.idsa.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    IPersonRepository personRepository;

    @Autowired
    ITutorRepository tutorRepository;

    @Autowired
    IUserMapper userMapper;

    @Override
    public List<UserResponse> findAll() {
        return userMapper.toResponseList(userRepository.findAll());
    }

    @Override
    public List<UserResponse> findAllActiveExceptAdmin() {
        List<UserEntity> userRepositories = userRepository.findAllByIsActiveTrue();
        userRepositories.removeIf(user -> "ROLE_ADMIN".equals(user.getRole().getRoleName()));
        return userMapper.toResponseList(userRepositories);
    }

    @Override
    public List<UserResponse> findAllInactiveExceptAdmin() {
        List<UserEntity> userRepositories = userRepository.findAllByIsActiveFalse();
        userRepositories.removeIf(user -> "ROLE_ADMIN".equals(user.getRole().getRoleName()));
        return userMapper.toResponseList(userRepositories);
    }

    @Transactional
    @Override
    public UserResponse createUser(UserRequest userRequest) throws DuplicateResourceException, UserRoleCreationDeniedException, ResourceNotFoundException {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new DuplicateResourceException("create", "User", "email", userRequest.getEmail());
        } else if (personRepository.existsByPhoneNumber(userRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("create", "User", "phoneNumber", userRequest.getPhoneNumber());
        } else if (personRepository.existsByTutor_EmployeeCode(userRequest.getKey())) {
            throw new DuplicateResourceException("create", "User", "key_employeeCode", userRequest.getKey());
        } else if (personRepository.existsByStudent_StudentCode(userRequest.getKey())) {
            throw new DuplicateResourceException("create", "User", "key_studentCode", userRequest.getKey());
        }

        // Obtener el rol del usuario, Si el rol no existe, lanzar una excepción
        RoleEntity roleEntity = roleRepository.findByRoleName(userRequest.getRole().getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("create", "Role", "RoleName", userRequest.getRole().getRoleId()));

        // Sí es rol admin denegar la creación
        if ("ROLE_ADMIN".equals(roleEntity.getRoleName())) throw new UserRoleCreationDeniedException();

        // Guardar la persona
        PersonEntity personEntity = userMapper.toPersonEntity(userRequest);
        personRepository.save(personEntity);

        // Guardar el tutor
        if (userRequest.getRole().getRoleId().equals("ROLE_TUTOR")) {
            TutorEntity tutorEntity = new TutorEntity();
            tutorEntity.setPerson(personEntity);
            tutorEntity.setEmployeeCode(userRequest.getKey());
            tutorEntity = tutorRepository.save(tutorEntity);
            personEntity.setTutor(tutorEntity);
        }

        // Guardar el usuario
        UserEntity userEntity = new UserEntity();
        userEntity.setPerson(personEntity);
        userEntity.setRole(roleEntity);
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setPassword(userRequest.getPassword());
        userEntity.setCreatedAt(ZonedDateTime.now());
        userEntity.setIsActive(true);
        userRepository.save(userEntity);

        return userMapper.toResponse(userEntity);
    }

    @Transactional
    @Override
    public UserResponse updateUser(Long userId, boolean isUpdatePassword, UpdateUserRequest updateUserRequest) throws ResourceNotFoundException, DuplicateResourceException, UserRoleCreationDeniedException {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("update", "User", userId));

        // Verificar la correcta validation de si ya existe
        if (!userEntity.getEmail().equals(updateUserRequest.getEmail()) && userRepository.existsByEmail(updateUserRequest.getEmail())) {
            throw new DuplicateResourceException("update", "User", "email", updateUserRequest.getEmail());
        } else if (!userEntity.getPerson().getPhoneNumber().equals(updateUserRequest.getPhoneNumber()) && personRepository.existsByPhoneNumber(updateUserRequest.getPhoneNumber())) {
            throw new DuplicateResourceException("update", "User", "phoneNumber", updateUserRequest.getPhoneNumber());
        } else if (userEntity.getPerson().getTutor() != null && !userEntity.getPerson().getTutor().getEmployeeCode().equals(updateUserRequest.getKey()) && personRepository.existsByTutor_EmployeeCode(updateUserRequest.getKey())) {
            throw new DuplicateResourceException("update", "User", "key_employeeCode", updateUserRequest.getKey());
        } else if (personRepository.existsByStudent_StudentCode(updateUserRequest.getKey())) {
            throw new DuplicateResourceException("update", "User", "key_studentCode", updateUserRequest.getKey());
        }

        RoleEntity newRole = roleRepository.findByRoleName(updateUserRequest.getRole().getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("update", "Role", "RoleName", updateUserRequest.getRole().getRoleId()));

        if ("ROLE_ADMIN".equals(newRole.getRoleName()))
            throw new UserRoleCreationDeniedException("Update of user with the role 'ADMIN' is denied");

        PersonEntity person = userEntity.getPerson();
        person.setName(updateUserRequest.getName());
        person.setFirstSurname(updateUserRequest.getFirstSurname());
        person.setSecondSurname(updateUserRequest.getSecondSurname());
        person.setPhoneNumber(updateUserRequest.getPhoneNumber());
        personRepository.save(person);

        // Actualizar datos del tutor
        if (updateUserRequest.getRole().getRoleId().equals("ROLE_TUTOR")) {
            TutorEntity tutor = userEntity.getPerson().getTutor();
            tutor.setPerson(person);
            tutor.setEmployeeCode(updateUserRequest.getKey());
            tutorRepository.save(tutor);
            person.setTutor(tutor);
        }

        // Actualizar datos del usuario
        userEntity.setEmail(updateUserRequest.getEmail());
        userEntity.setRole(newRole);
        if (isUpdatePassword) userEntity.setPassword(updateUserRequest.getPassword());

        userRepository.save(userEntity);

        return userMapper.toResponse(userEntity);
    }

    @Override
    public void deleteUser(Long userId) throws ResourceNotFoundException {
        // Si tiene grupos asignados se decir que no se puede elimininar por que tiene grupos asignados, que en confiuguración grupo cambie el tutor por otro
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("delete", "User", userId));

        userRepository.delete(userEntity);
    }
}
