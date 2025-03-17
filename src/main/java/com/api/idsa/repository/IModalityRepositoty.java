package com.api.idsa.repository;

import com.api.idsa.model.ModalityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IModalityRepositoty extends JpaRepository<ModalityEntity, Long> {

    boolean existsByModalityName(String modalityName);

}
