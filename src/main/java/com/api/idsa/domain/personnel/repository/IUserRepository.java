package com.api.idsa.domain.personnel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.personnel.model.UserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, UUID> {

    Page<UserEntity> findAllByRoleRoleNameIsNot(String roleName, Pageable pageable);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);

    @Query(
        value = "select * from search_users(:search, :excludeAdmin)",
        nativeQuery = true
    )
    Page<UserEntity> findUsersBySearchTerm(
        @Param("search") String search,
        @Param("excludeAdmin") boolean excludeAdmin,
        Pageable pageable
    );
}
