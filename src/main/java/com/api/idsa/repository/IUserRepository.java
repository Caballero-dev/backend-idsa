package com.api.idsa.repository;

import com.api.idsa.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findAllByIsActiveTrue();

    List<UserEntity> findAllByIsActiveFalse();

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

}
