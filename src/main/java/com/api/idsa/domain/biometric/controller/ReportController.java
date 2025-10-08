package com.api.idsa.domain.biometric.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.biometric.dto.response.ReportResponse;
import com.api.idsa.domain.biometric.dto.response.ReportSummaryResponse;
import com.api.idsa.domain.biometric.service.IReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Tag(
	name = "Reportes Biométricos",
	description = "Endpoints para la consulta de reportes biométricos de estudiantes. Permite visualizar los datos de temperatura, frecuencia cardíaca, presión arterial e imágenes faciales capturadas por los dispositivos IoT."
)
@RestController
@RequestMapping("/api/common/reports")
public class ReportController {

	@Autowired
	IReportService reportService;

	@Operation(
		summary = "Obtener todos los reportes biométricos",
		description = "Retorna una lista paginada de todos los reportes biométricos registrados en el sistema, ordenados por fecha de creación."
	)
	@GetMapping
	public ResponseEntity<ApiResponse<List<ReportResponse>>> getAllReports(
		@Parameter(description = "Número de página (inicia en 0)", example = "0")
		@RequestParam(defaultValue = "0") int page,
		@Parameter(description = "Cantidad de elementos por página", example = "100")
		@RequestParam(defaultValue = "100") int size
	) {
		Pageable pageable = Pageable.ofSize(size).withPage(page);
		Page<ReportResponse> reportPage = reportService.findAll(pageable);
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<List<ReportResponse>>(
				HttpStatus.OK,
				"Reports retrieved successfully",
				reportPage.getContent(),
				PageInfo.fromPage(reportPage)
			)
		);
	}

	@Operation(
		summary = "Obtener reportes biométricos de un estudiante",
		description = "Retorna una lista paginada de todos los reportes biométricos asociados a un estudiante específico, ordenados por fecha de creación."
	)
	@GetMapping("/student/{studentId}")
	public ResponseEntity<ApiResponse<List<ReportResponse>>> getReportsByStudentId(
		@Parameter(description = "Identificador único del estudiante", required = true)
		@PathVariable UUID studentId,
		@Parameter(description = "Número de página (inicia en 0)", example = "0")
		@RequestParam(defaultValue = "0") int page,
		@Parameter(description = "Cantidad de elementos por página", example = "100")
		@RequestParam(defaultValue = "100") int size
	) {
		Pageable pageable = Pageable.ofSize(size).withPage(page);
		Page<ReportResponse> reportPage = reportService.getReportsByStudentId(studentId, pageable);
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<List<ReportResponse>>(
				HttpStatus.OK,
				"Reports retrieved successfully",
				reportPage.getContent(),
				PageInfo.fromPage(reportPage)
			)
		);
	}

	@Operation(
		summary = "Obtener resumen estadístico de reportes",
		description = "Retorna un resumen con estadísticas generales de reportes biométricos y predicciones de riesgo de deserción de todos los estudiantes."
	)
	@GetMapping("/summary")
	public ResponseEntity<ApiResponse<ReportSummaryResponse>> getReportSummary() {
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<ReportSummaryResponse>(
				HttpStatus.OK,
				"Report summary retrieved successfully",
				reportService.getReportSummary()
			)
		);
	}

}
