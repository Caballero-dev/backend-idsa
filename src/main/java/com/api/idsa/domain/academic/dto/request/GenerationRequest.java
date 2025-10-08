package com.api.idsa.domain.academic.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

import com.api.idsa.common.validation.annotation.YearRange;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@YearRange
@Schema(name = "GenerationRequest", description = "Solicitud para crear o actualizar una generación académica")
public class GenerationRequest {

	@Schema(
		description = "Año de inicio de la generación (debe ser menor al año de fin)",
		example = "2024-01-01T00:00:00Z",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull(message = "Start year is required")
	private ZonedDateTime yearStart;

	@Schema(
		description = "Año de fin de la generación (debe ser mayor al año de inicio)",
		example = "2027-12-31T23:59:59Z",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull(message = "End year is required")
	private ZonedDateTime yearEnd;
}
