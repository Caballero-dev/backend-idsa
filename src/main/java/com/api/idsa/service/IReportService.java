package com.api.idsa.service;

import com.api.idsa.dto.response.ReportResponse;

import java.util.List;

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
