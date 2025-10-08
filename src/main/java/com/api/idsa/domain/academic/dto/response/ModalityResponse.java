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
@Schema(name = "ModalityResponse", description = "Respuesta con la información de una modalidad educativa")
public class ModalityResponse {

	@Schema(description = "Identificador único de la modalidad", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID modalityId;

	@Schema(description = "Nombre de la modalidad educativa", example = "Escolarizada")
	private String name;

}
