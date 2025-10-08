package com.api.idsa.domain.biometric.dto.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "BiometricDataRequest", description = "Solicitud para enviar datos biométricos de un estudiante desde el dispositivo IoT")
public class BiometricDataRequest {

	@Schema(
		description = "Identificador único del estudiante (UUID)",
		example = "550e8400-e29b-41d4-a716-446655440000",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotBlank(message = "Student ID cannot be blank")
	private String studentId;

	@Schema(
		description = "Temperatura corporal del estudiante en grados Celsius",
		example = "36.5",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull(message = "Temperature cannot be null")
	private BigDecimal temperature;

	@Schema(
		description = "Frecuencia cardíaca del estudiante en latidos por minuto",
		example = "75",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull(message = "Heart rate cannot be null")
	private BigDecimal heartRate;

	@Schema(
		description = "Presión arterial sistólica (máxima)",
		example = "120",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull(message = "Systolic blood pressure cannot be null")
	private BigDecimal systolicBloodPressure;

	@Schema(
		description = "Presión arterial diastólica (mínima)",
		example = "80",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull(message = "Diastolic blood pressure cannot be null")
	private BigDecimal diastolicBloodPressure;

	@Schema(
		description = "Imagen facial del estudiante codificada en Base64",
		example = "data:image/jpeg;base64,/9j/4AAQSkZJRg...",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotBlank(message = "Image cannot be blank")
	private String image;

}
