package com.api.idsa.domain.academic.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.academic.model.GradeEntity;

import java.util.UUID;

@Repository
public interface IGradeRepository extends JpaRepository<GradeEntity, UUID> {

    boolean existsByGradeName(String gradeName);

    Page<GradeEntity> findByGradeNameContainingIgnoreCase(String gradeName, Pageable pageable);

}
