package com.api.idsa.domain.personnel.dto.response;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "TutorResponse", description = "Respuesta con la información de un tutor")
public class TutorResponse {

	@Schema(description = "Identificador único del tutor", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID tutorId;

	@Schema(description = "Correo electrónico del tutor", example = "tutor@example.com")
	private String email;

	@Schema(description = "Nombre(s) del tutor", example = "María Elena")
	private String name;

	@Schema(description = "Apellido paterno del tutor", example = "Pérez")
	private String firstSurname;

	@Schema(description = "Apellido materno del tutor", example = "Martínez")
	private String secondSurname;

	@Schema(description = "Número de teléfono del tutor", example = "5598765432")
	private String phoneNumber;

	@Schema(description = "Número de empleado del tutor", example = "EMP2024001")
	private String employeeCode;

}
