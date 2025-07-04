package com.api.idsa.domain.academic.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.academic.model.GenerationEntity;

import java.time.ZonedDateTime;

@Repository
public interface IGenerationRepository extends JpaRepository<GenerationEntity, Long> {

    boolean existsByStartYearAndEndYear(ZonedDateTime startYear, ZonedDateTime endYear);

    @Query(value = "select * from generations where to_char(start_year, 'YYYY-MM-DD') like %:search% or to_char(end_year, 'YYYY-MM-DD') like %:search%", nativeQuery = true)
    Page<GenerationEntity> findByStartYearOrEndYearContaining(@Param("search") String search, Pageable pageable);

}
