package com.api.idsa.domain.biometric.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.idsa.domain.biometric.model.ReportImageEntity;

public interface IReportImageRepository extends JpaRepository<ReportImageEntity, Long> {

}
