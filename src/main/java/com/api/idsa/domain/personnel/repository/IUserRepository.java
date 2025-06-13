package com.api.idsa.domain.personnel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.personnel.model.UserEntity;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    Page<UserEntity> findAllByRole_RoleNameIsNot(String roleName, Pageable pageable);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

}
