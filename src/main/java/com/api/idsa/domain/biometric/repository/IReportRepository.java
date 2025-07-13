package com.api.idsa.domain.biometric.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.idsa.domain.biometric.model.ReportEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IReportRepository extends JpaRepository<ReportEntity, UUID> {
    
    ReportEntity findByStudent_StudentId(UUID studentId);
    
    boolean existsByStudent_StudentId(UUID studentId);

    Page<ReportEntity> findByStudentStudentIdOrderByCreatedAtDesc(UUID studentId, Pageable pageable);

    Optional<ReportEntity> findFirstByStudent_StudentIdOrderByCreatedAtDesc(UUID studentStudentId);

    @Query(value = "select count(distinct student_id) from reports", nativeQuery = true)
    Integer countStudentsWithReports();

    @Query(value = "select * from count_students_by_prediction(:predictionResult)", nativeQuery = true)
    Integer countStudentsByPredictionResult(@Param("predictionResult") String predictionResult);

}
