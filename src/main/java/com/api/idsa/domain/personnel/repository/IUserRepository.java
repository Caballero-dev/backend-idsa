package com.api.idsa.domain.personnel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.idsa.domain.personnel.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findAllByIsActiveTrue();

    List<UserEntity> findAllByIsActiveFalse();

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

}
