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
@Schema(name = "GroupResponse", description = "Respuesta con la información de un grupo escolar")
public class GroupResponse {

	@Schema(description = "Identificador único del grupo", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID groupId;

	@Schema(description = "Identificador del grupo", example = "A")
	private String name;

}
