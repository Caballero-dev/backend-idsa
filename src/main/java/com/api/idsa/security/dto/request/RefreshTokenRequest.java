package com.api.idsa.security.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "RefreshTokenRequest", description = "Solicitud para refrescar el token de acceso utilizando un refresh token")
public class RefreshTokenRequest {

	@Schema(
		description = "Token de acceso actual (puede estar expirado o próximo a expirar)",
		example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNjE2MjM5MDIyfQ...",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotBlank(message = "Access token cannot be blank")
	private String accessToken;

	@Schema(
		description = "Token de refresco válido para generar un nuevo access token",
		example = "dGhpcyBpcyBhIHJlZnJlc2ggdG9rZW4gZXhhbXBsZQ...",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotBlank(message = "Refresh token cannot be blank")
	private String refreshToken;
	
}
