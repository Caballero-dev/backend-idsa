package com.api.idsa.security.dto.request;

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
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "PasswordSetRequest", description = "Solicitud para verificar email y establecer contraseña inicial")
public class PasswordSetRequest {

	@Schema(
		description = "Token de verificación de email enviado al correo del usuario",
		example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotBlank(message = "Token cannot be blank")
	private String token;

	@Schema(
		description = "Contraseña inicial del usuario (entre 8 y 20 caracteres, debe contener mayúsculas, minúsculas, números y caracteres especiales)",
		example = "MiP@ssw0rd123",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotBlank(message = "Password cannot be blank")
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	@Pattern(regexp = RegexPatterns.PASSWORD, message = "Password contains invalid characters")
	private String password;

}
