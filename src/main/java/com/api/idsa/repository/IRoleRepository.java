package com.api.idsa.repository;

import com.api.idsa.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {

}
