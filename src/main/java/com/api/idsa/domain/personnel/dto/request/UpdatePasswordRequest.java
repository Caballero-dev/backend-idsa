package com.api.idsa.domain.personnel.dto.request;

import com.api.idsa.common.util.RegexPatterns;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UpdatePasswordRequest", description = "Solicitud para actualizar la contraseña de un usuario")
public class UpdatePasswordRequest {

	@Schema(
		description = "Contraseña actual del usuario (para verificación)",
		example = "MiP@ssw0rdActual",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 8,
		maxLength = 20
	)
	@NotBlank(message = "Password cannot be blank")
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	@Pattern(regexp = RegexPatterns.PASSWORD, message = "Password contains invalid characters")
	private String currentPassword;

	@Schema(
		description = "Nueva contraseña del usuario (debe contener mayúsculas, minúsculas, números y caracteres especiales)",
		example = "NuevaP@ssw0rd123",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 8,
		maxLength = 20
	)
	@NotBlank(message = "Password cannot be blank")
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	@Pattern(regexp = RegexPatterns.PASSWORD, message = "Password contains invalid characters")
	private String newPassword;

}
