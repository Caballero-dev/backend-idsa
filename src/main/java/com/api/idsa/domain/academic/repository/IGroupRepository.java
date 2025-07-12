package com.api.idsa.domain.academic.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.academic.model.GroupEntity;

import java.util.UUID;

@Repository
public interface IGroupRepository extends JpaRepository<GroupEntity, UUID> {

    boolean existsByGroupName(String groupName);

    Page<GroupEntity> findByGroupNameContainingIgnoreCase(String groupName, Pageable pageable);

}
