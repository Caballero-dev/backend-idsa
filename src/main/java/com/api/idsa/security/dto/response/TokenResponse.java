package com.api.idsa.security.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "TokenResponse", description = "Respuesta con los tokens de autenticación del usuario")
public class TokenResponse {

	@Schema(
		description = "Token de acceso (JWT) para autenticar las peticiones del usuario",
		example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwiaWF0IjoxNjE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
	)
	private String accessToken;

	@Schema(
		description = "Token de refresco para obtener un nuevo access token cuando expire",
		example = "dGhpcyBpcyBhIHJlZnJlc2ggdG9rZW4gZXhhbXBsZSBmb3IgdGhlIGFwcGxpY2F0aW9u"
	)
	private String refreshToken;

}
