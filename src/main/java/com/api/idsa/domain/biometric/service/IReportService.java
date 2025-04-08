package com.api.idsa.domain.biometric.service;

import java.util.List;

import com.api.idsa.domain.biometric.dto.response.ReportResponse;

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

}
