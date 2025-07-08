package com.api.idsa.domain.biometric.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.biometric.model.ReportEntity;

import java.util.Optional;

@Repository
public interface IReportRepository extends JpaRepository<ReportEntity, Long> {
    
    ReportEntity findByStudent_StudentId(Long studentId);
    
    boolean existsByStudent_StudentId(Long studentId);

    Page<ReportEntity> findByStudentStudentIdOrderByCreatedAtDesc(Long studentId, Pageable pageable);

    Optional<ReportEntity> findFirstByStudent_StudentIdOrderByCreatedAtDesc(Long studentStudentId);

    @Query(value = "select count(distinct student_id) from reports", nativeQuery = true)
    Integer countStudentsWithReports();

    @Query(value = "select count(*) from reports where prediction_result = :predictionResult", nativeQuery = true)
    Integer countStudentsByPredictionResult(@Param("predictionResult") String predictionResult);

}
