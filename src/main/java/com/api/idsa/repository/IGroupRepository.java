package com.api.idsa.repository;

import com.api.idsa.model.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGroupRepository extends JpaRepository<GroupEntity, Long> {

    boolean existsByGroupName(String groupName);

}
