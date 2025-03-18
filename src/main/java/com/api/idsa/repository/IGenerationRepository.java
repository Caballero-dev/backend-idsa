package com.api.idsa.repository;

import com.api.idsa.model.GenerationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface IGenerationRepository extends JpaRepository<GenerationEntity, Long> {

    boolean existsByStartYearAndEndYear(LocalDate startYear, LocalDate endYear);

}
