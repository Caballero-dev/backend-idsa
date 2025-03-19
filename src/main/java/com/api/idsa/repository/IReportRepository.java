package com.api.idsa.repository;

import com.api.idsa.model.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReportRepository extends JpaRepository<ReportEntity, Long> {

}
