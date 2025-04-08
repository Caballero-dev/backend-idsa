package com.api.idsa.domain.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.idsa.domain.academic.model.CampusEntity;

public interface ICampusRepository extends JpaRepository<CampusEntity, Long> {

    boolean existsByCampusName(String campusName);

}
