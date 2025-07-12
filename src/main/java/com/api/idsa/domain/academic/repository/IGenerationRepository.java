package com.api.idsa.domain.academic.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.academic.model.GenerationEntity;

import java.time.ZonedDateTime;
import java.util.UUID;

@Repository
public interface IGenerationRepository extends JpaRepository<GenerationEntity, UUID> {

    boolean existsByStartYearAndEndYear(ZonedDateTime startYear, ZonedDateTime endYear);

    @Query(
            value = "select * from search_generations(:search)",
            nativeQuery = true
    )
    Page<GenerationEntity> findGenerationsBySearchTerm(@Param("search") String search, Pageable pageable);

}
