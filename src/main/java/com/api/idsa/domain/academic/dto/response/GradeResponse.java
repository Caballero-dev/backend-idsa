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
@Schema(name = "GradeResponse", description = "Respuesta con la información de un grado escolar")
public class GradeResponse {

	@Schema(description = "Identificador único del grado", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID gradeId;

	@Schema(description = "Nombre del grado escolar", example = "Primero")
	private String name;

}
