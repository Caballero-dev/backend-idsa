package com.api.idsa.domain.academic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.academic.model.GradeEntity;

@Repository
public interface IGradeRepository extends JpaRepository<GradeEntity, Long> {

    boolean existsByGradeName(String gradeName);

}
