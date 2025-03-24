package com.api.idsa.repository;

import com.api.idsa.model.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReportRepository extends JpaRepository<ReportEntity, Long> {
    ReportEntity findByStudent_StudentId(Long studentId);
    List<ReportEntity> findByStudentStudentId(Long studentId);
}
