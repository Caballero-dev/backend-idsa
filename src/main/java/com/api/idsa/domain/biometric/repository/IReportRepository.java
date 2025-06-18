package com.api.idsa.domain.biometric.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.biometric.model.ReportEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface IReportRepository extends JpaRepository<ReportEntity, Long> {
    
    ReportEntity findByStudent_StudentId(Long studentId);
    
    boolean existsByStudent_StudentId(Long studentId);

    List<ReportEntity> findByStudentStudentId(Long studentId);

    Optional<ReportEntity> findFirstByStudent_StudentIdOrderByCreatedAtDesc(Long studentStudentId);

}
