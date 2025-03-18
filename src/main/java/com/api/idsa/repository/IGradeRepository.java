package com.api.idsa.repository;

import com.api.idsa.model.GradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGradeRepository extends JpaRepository<GradeEntity, Long> {

    boolean existsByGradeName(String gradeName);

}
