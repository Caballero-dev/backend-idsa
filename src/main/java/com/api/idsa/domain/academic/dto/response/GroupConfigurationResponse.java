package com.api.idsa.domain.academic.dto.response;

import com.api.idsa.domain.personnel.dto.response.TutorResponse;

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
@Schema(name = "GroupConfigurationResponse", description = "Respuesta con la información completa de una configuración de grupo académico")
public class GroupConfigurationResponse {

	@Schema(description = "Identificador único de la configuración del grupo", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID groupConfigurationId;

	@Schema(description = "Información del tutor asignado al grupo")
	private TutorResponse tutor;

	@Schema(description = "Información del plantel educativo")
	private CampusResponse campus;

	@Schema(description = "Información de la especialidad técnica")
	private SpecialtyResponse specialty;

	@Schema(description = "Información de la modalidad educativa")
	private ModalityResponse modality;

	@Schema(description = "Información del grado escolar")
	private GradeResponse grade;

	@Schema(description = "Información del grupo")
	private GroupResponse group;

	@Schema(description = "Información de la generación académica")
	private GenerationResponse generation;

}
