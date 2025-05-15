package com.api.idsa.domain.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.academic.model.ModalityEntity;

@Repository
public interface IModalityRepository extends JpaRepository<ModalityEntity, Long> {

    boolean existsByModalityName(String modalityName);

}
