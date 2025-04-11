package com.api.idsa.domain.personnel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.personnel.model.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    // List<UserEntity> findAllByIsActiveTrue();
    List<UserEntity> findAllByIsActiveTrueAndRole_RoleNameIsNot(String roleName);

    // List<UserEntity> findAllByIsActiveFalse();
    List<UserEntity> findAllByIsActiveFalseAndRole_RoleNameIsNot(String roleName);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

}
