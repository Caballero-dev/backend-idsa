package com.api.idsa.domain.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.idsa.domain.academic.model.ModalityEntity;

public interface IModalityRepositoty extends JpaRepository<ModalityEntity, Long> {

    boolean existsByModalityName(String modalityName);

}
