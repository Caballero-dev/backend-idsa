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
@Schema(name = "UserResponse", description = "Respuesta con la información completa de un usuario del sistema")
public class UserResponse {

	@Schema(description = "Identificador único del usuario", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID userId;

	@Schema(description = "Correo electrónico del usuario", example = "usuario@example.com")
	private String email;

	@Schema(description = "Rol del usuario en el sistema")
	private RoleResponse role;

	@Schema(description = "Nombre(s) del usuario", example = "Pedro Luis")
	private String name;

	@Schema(description = "Apellido paterno del usuario", example = "Ramírez")
	private String firstSurname;

	@Schema(description = "Apellido materno del usuario", example = "González")
	private String secondSurname;

	@Schema(description = "Número de empleado del usuario", example = "USR2024001")
	private String key;

	@Schema(description = "Número de teléfono del usuario", example = "5587654321")
	private String phoneNumber;

	@Schema(description = "Fecha y hora de creación del usuario", example = "2024-01-15T10:30:00Z")
	private ZonedDateTime createdAt;

	@Schema(description = "Indica si el usuario está activo en el sistema", example = "true")
	private Boolean isActive;

	@Schema(description = "Indica si el usuario ha verificado su correo electrónico", example = "true")
	private Boolean isVerifiedEmail;

}
