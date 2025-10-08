package com.api.idsa.domain.biometric.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ReportSummaryResponse", description = "Resumen estadístico de reportes biométricos y predicciones de estudiantes")
public class ReportSummaryResponse {

	@Schema(description = "Total de estudiantes registrados en el sistema", example = "150")
	private Integer totalStudents;

	@Schema(description = "Cantidad de estudiantes que tienen al menos un reporte biométrico", example = "120")
	private Integer studentsWithReports;

	@Schema(description = "Cantidad de estudiantes sin reportes biométricos", example = "30")
	private Integer studentsWithoutReports;

	@Schema(description = "Cantidad de estudiantes con predicción de riesgo bajo de deserción", example = "80")
	private Integer studentsWithLowProbability;

	@Schema(description = "Cantidad de estudiantes con predicción de riesgo medio de deserción", example = "30")
	private Integer studentsWithMediumProbability;

	@Schema(description = "Cantidad de estudiantes con predicción de riesgo alto de deserción", example = "10")
	private Integer studentsWithHighProbability;

}
