package com.api.idsa.domain.biometric.service;

import java.time.ZonedDateTime;
import java.util.List;

import com.api.idsa.domain.biometric.dto.response.ReportResponse;
import com.api.idsa.domain.personnel.model.StudentEntity;

public interface IReportService {

    // este seria para las graficas

    /**
     * Obtiene todos los reportes registrados.
     *
     * @return Lista de {@link ReportResponse} con la información de todos los reportes.
     */
    List<ReportResponse> findAll();

    /**
     * Obtiene todos los reportes registrados para un estudiante específico.
     *
     * @return Lista de {@link ReportResponse} con la información de los reportes del estudiante.
     */
    List<ReportResponse> getReportsByStudentId(Long studentId);

    /**
     * Crea un reporte.
     * 
     * @param studentEntity Entidad del estudiante para el cual se generará el reporte.
     * @param endDate Fecha del ultimo registro del dato biometrico.
     */
    void createReport(StudentEntity studentEntity, ZonedDateTime endDate);

}
