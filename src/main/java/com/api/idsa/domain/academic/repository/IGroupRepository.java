package com.api.idsa.domain.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.academic.model.GroupEntity;

@Repository
public interface IGroupRepository extends JpaRepository<GroupEntity, Long> {

    boolean existsByGroupName(String groupName);

}
