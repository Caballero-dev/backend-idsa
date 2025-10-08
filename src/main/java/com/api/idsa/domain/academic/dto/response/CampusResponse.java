package com.api.idsa.domain.academic.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CampusResponse", description = "Respuesta con la información de un plantel educativo")
public class CampusResponse {

	@Schema(description = "Identificador único del plantel", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID campusId;

	@Schema(description = "Nombre del plantel educativo", example = "Campus Central")
	private String name;

}
