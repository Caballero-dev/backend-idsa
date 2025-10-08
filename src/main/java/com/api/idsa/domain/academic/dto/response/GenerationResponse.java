package com.api.idsa.domain.academic.dto.response;

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
@Schema(name = "GenerationResponse", description = "Respuesta con la información de una generación académica")
public class GenerationResponse {

	@Schema(description = "Identificador único de la generación", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID generationId;

	@Schema(description = "Año de inicio de la generación", example = "2024-01-01T00:00:00Z")
	private ZonedDateTime yearStart;

	@Schema(description = "Año de fin de la generación", example = "2027-12-31T23:59:59Z")
	private ZonedDateTime yearEnd;

}
