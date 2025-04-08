package com.api.idsa.domain.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.idsa.domain.academic.model.SpecialityEntity;

public interface ISpecialityRespository extends JpaRepository<SpecialityEntity, Long> {

    boolean existsBySpecialityName(String specialityName);

    boolean existsByShortName(String shortName);

}
