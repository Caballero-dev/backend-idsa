package com.api.idsa.domain.personnel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.personnel.model.RoleEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {

    List<RoleEntity> findAllByRoleNameNot(String roleName);

    Optional<RoleEntity> findByRoleName(String roleName);

}
