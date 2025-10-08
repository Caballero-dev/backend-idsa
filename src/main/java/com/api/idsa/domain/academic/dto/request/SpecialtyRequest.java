package com.api.idsa.domain.academic.dto.request;

import com.api.idsa.common.util.RegexPatterns;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "SpecialtyRequest", description = "Solicitud para crear o actualizar una especialidad técnica")
public class SpecialtyRequest {

	@Schema(
		description = "Nombre completo de la especialidad técnica",
		example = "Programación",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 3,
		maxLength = 100
	)
	@NotBlank(message = "Name cannot be blank")
	@Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
	@Pattern(regexp = RegexPatterns.ONLY_LETTERS, message = "Name can only contain letters and spaces")
	private String name;

	@Schema(
		description = "Nombre corto o abreviatura de la especialidad",
		example = "PROG",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 2,
		maxLength = 10
	)
	@NotBlank(message = "Short name cannot be blank")
	@Size(min = 2, max = 10, message = "Short name must be between 2 and 10 characters")
	@Pattern(regexp = RegexPatterns.ONLY_PLAIN_LETTERS, message = "Short name can only contain letters")
	private String shortName;

}
