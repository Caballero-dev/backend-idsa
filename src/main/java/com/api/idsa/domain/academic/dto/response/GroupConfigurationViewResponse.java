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
@Schema(name = "GroupConfigurationViewResponse", description = "Respuesta con vista simplificada de la configuración de grupo con información concatenada")
public class GroupConfigurationViewResponse {

	@Schema(description = "Identificador único de la configuración", example = "550e8400-e29b-41d4-a716-446655440000")
	private UUID id;

	@Schema(description = "Nombre completo del tutor", example = "Juan Pérez García")
	private String tutor;

	@Schema(description = "Nombre del plantel", example = "Campus Central")
	private String campus;

	@Schema(description = "Nombre completo de la especialidad", example = "Programación")
	private String specialty;

	@Schema(description = "Nombre corto de la especialidad", example = "PROG")
	private String specialtyShortName;

	@Schema(description = "Nombre de la modalidad", example = "Escolarizada")
	private String modality;

	@Schema(description = "Nombre del grado", example = "Primero")
	private String grade;

	@Schema(description = "Identificador del grupo", example = "A")
	private String group;

	@Schema(description = "Rango de la generación", example = "2024-2027")
	private String generation;

	@Schema(description = "Número de estudiantes en el grupo", example = "30")
	private Integer students;

}
