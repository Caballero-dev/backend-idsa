package com.api.idsa.domain.biometric.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import com.api.idsa.domain.personnel.dto.response.StudentResponse;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ReportResponse", description = "Respuesta con la información completa de un reporte biométrico")
public class ReportResponse {

	@Schema(description = "Identificador único del reporte", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID reportId;

	@Schema(description = "Información del estudiante asociado al reporte")
	private StudentResponse student;

	@Schema(description = "Temperatura corporal registrada en grados Celsius", example = "36.5")
	private BigDecimal temperature;

	@Schema(description = "Frecuencia cardíaca registrada en latidos por minuto", example = "75")
	private BigDecimal heartRate;

	@Schema(description = "Presión arterial sistólica registrada", example = "120")
	private BigDecimal systolicBloodPressure;

	@Schema(description = "Presión arterial diastólica registrada", example = "80")
	private BigDecimal diastolicBloodPressure;

	@Schema(description = "Lista de URLs de las imágenes faciales capturadas", example = "[\"image_1234.jpg\", \"image_5678.jpg\"]")
	private List<String> images;

	@Schema(description = "Fecha y hora de creación del reporte", example = "2024-01-15T10:30:00Z")
	private ZonedDateTime createdAt;

}
