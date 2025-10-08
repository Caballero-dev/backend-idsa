package com.api.idsa.domain.personnel.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserProfileResponse", description = "Respuesta con el perfil del usuario autenticado")
public class UserProfileResponse {

	@Schema(description = "Identificador único del usuario", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID userId;

	@Schema(description = "Nombre(s) del usuario", example = "Pedro Luis")
	private String name;

	@Schema(description = "Apellido paterno del usuario", example = "Ramírez")
	private String firstSurname;

	@Schema(description = "Apellido materno del usuario", example = "González")
	private String secondSurname;

	@Schema(description = "Correo electrónico del usuario", example = "usuario@example.com")
	private String email;

	@Schema(description = "Número de teléfono del usuario", example = "5587654321")
	private String phone;

	@Schema(description = "Nombre del rol del usuario", example = "Tutor")
	private String roleName;

	@Schema(description = "Número de empleado", example = "USR2024001")
	private String key;

	@Schema(description = "Fecha y hora de creación del usuario", example = "2024-01-15T10:30:00Z")
	private ZonedDateTime createdAt;

}
