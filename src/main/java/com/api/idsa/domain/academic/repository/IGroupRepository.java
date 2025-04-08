package com.api.idsa.domain.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.idsa.domain.academic.model.GroupEntity;

public interface IGroupRepository extends JpaRepository<GroupEntity, Long> {

    boolean existsByGroupName(String groupName);

}
