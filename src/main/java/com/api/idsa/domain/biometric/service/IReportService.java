package com.api.idsa.domain.biometric.service;

import java.time.ZonedDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.api.idsa.domain.biometric.dto.response.ReportResponse;
import com.api.idsa.domain.personnel.model.StudentEntity;

public interface IReportService {

    /**
     * Obtiene todos los reportes registrados.
     *
     * @return Lista de {@link ReportResponse} con la información de todos los reportes.
     */
    Page<ReportResponse> findAll(Pageable pageable);

    /**
     * Obtiene todos los reportes registrados para un estudiante específico.
     *
     * @return Lista de {@link ReportResponse} con la información de los reportes del estudiante.
     */
    Page<ReportResponse> getReportsByStudentId(Long studentId, Pageable pageable);

    /**
     * Crea un reporte.
     * 
     * @param studentEntity Entidad del estudiante para el cual se generará el reporte.
     * @param endDate Fecha del ultimo registro del dato biometrico.
     */
    void createReport(StudentEntity studentEntity, ZonedDateTime endDate);

}
