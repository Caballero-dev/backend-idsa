package com.api.idsa.domain.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.academic.model.CampusEntity;

@Repository
public interface ICampusRepository extends JpaRepository<CampusEntity, Long> {

    boolean existsByCampusName(String campusName);

}
