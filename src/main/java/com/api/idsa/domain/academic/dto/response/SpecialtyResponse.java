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
@Schema(name = "SpecialtyResponse", description = "Respuesta con la información de una especialidad técnica")
public class SpecialtyResponse {

	@Schema(description = "Identificador único de la especialidad", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID specialtyId;

	@Schema(description = "Nombre completo de la especialidad", example = "Programación")
	private String name;

	@Schema(description = "Nombre corto o abreviatura de la especialidad", example = "PROG")
	private String shortName;

}
