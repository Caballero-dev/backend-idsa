package com.api.idsa.repository;

import com.api.idsa.model.CampusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICampusRepository extends JpaRepository<CampusEntity, Long> {

    boolean existsByCampusName(String campusName);

}
