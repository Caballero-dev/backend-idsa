package com.api.idsa.domain.personnel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.idsa.domain.personnel.model.RoleEntity;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRoleName(String roleName);

}
