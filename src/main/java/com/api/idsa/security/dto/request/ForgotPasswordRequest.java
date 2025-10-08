package com.api.idsa.security.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ForgotPasswordRequest", description = "Solicitud para restablecer contraseña olvidada")
public class ForgotPasswordRequest {

	@Schema(
		description = "Dirección de correo electrónico del usuario que solicita restablecer su contraseña",
		example = "usuario@example.com",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Invalid email format")
	private String email;

}
