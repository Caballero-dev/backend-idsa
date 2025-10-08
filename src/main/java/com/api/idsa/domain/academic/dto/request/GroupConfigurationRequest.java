package com.api.idsa.domain.academic.dto.request;

import com.api.idsa.domain.academic.dto.response.CampusResponse;
import com.api.idsa.domain.academic.dto.response.GenerationResponse;
import com.api.idsa.domain.academic.dto.response.GradeResponse;
import com.api.idsa.domain.academic.dto.response.GroupResponse;
import com.api.idsa.domain.academic.dto.response.ModalityResponse;
import com.api.idsa.domain.academic.dto.response.SpecialtyResponse;
import com.api.idsa.domain.personnel.dto.response.TutorResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "GroupConfigurationRequest", description = "Solicitud para crear o actualizar una configuración de grupo académico completa")
public class GroupConfigurationRequest {

	@Schema(
		description = "Tutor asignado al grupo",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull(message = "Tutor cannot be null")
	private TutorResponse tutor;

	@Schema(
		description = "Plantel educativo donde se imparte el grupo",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull(message = "Campus cannot be null")
	private CampusResponse campus;

	@Schema(
		description = "Especialidad técnica del grupo",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull(message = "Specialty cannot be null")
	private SpecialtyResponse specialty;

	@Schema(
		description = "Modalidad educativa del grupo",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull(message = "Modality cannot be null")
	private ModalityResponse modality;

	@Schema(
		description = "Grado escolar del grupo",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull(message = "Grade cannot be null")
	private GradeResponse grade;

	@Schema(
		description = "Identificador del grupo (A, B, C, etc.)",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull(message = "Group cannot be null")
	private GroupResponse group;

	@Schema(
		description = "Generación académica del grupo",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull(message = "Generation cannot be null")
	private GenerationResponse generation;

}
