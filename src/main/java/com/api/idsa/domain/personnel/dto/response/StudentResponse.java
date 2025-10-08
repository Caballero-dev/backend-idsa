package com.api.idsa.domain.personnel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import com.api.idsa.domain.biometric.enums.PredictionLevel;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "StudentResponse", description = "Respuesta con la información de un estudiante")
public class StudentResponse {

	@Schema(description = "Identificador único del estudiante", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID studentId;

	@Schema(description = "Matrícula del estudiante", example = "2024001234")
	private String studentCode;

	@Schema(description = "Nombre(s) del estudiante", example = "Juan Carlos")
	private String name;

	@Schema(description = "Apellido paterno del estudiante", example = "García")
	private String firstSurname;

	@Schema(description = "Apellido materno del estudiante", example = "López")
	private String secondSurname;

	@Schema(description = "Número de teléfono del estudiante", example = "5512345678")
	private String phoneNumber;

	@Schema(description = "Resultado de la última predicción del modelo (bajo, medio, alto)", example = "BAJO")
	private PredictionLevel predictionResult;

}
