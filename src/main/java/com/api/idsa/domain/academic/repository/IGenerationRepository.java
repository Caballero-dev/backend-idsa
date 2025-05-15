package com.api.idsa.domain.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.academic.model.GenerationEntity;

import java.time.LocalDate;

@Repository
public interface IGenerationRepository extends JpaRepository<GenerationEntity, Long> {

    boolean existsByStartYearAndEndYear(LocalDate startYear, LocalDate endYear);

}
