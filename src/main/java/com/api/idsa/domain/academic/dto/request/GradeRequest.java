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
@Schema(name = "GradeRequest", description = "Solicitud para crear o actualizar un grado escolar")
public class GradeRequest {

	@Schema(
		description = "Nombre del grado escolar (primero, segundo, tercero, etc.)",
		example = "Primero",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 2,
		maxLength = 30
	)
	@NotBlank(message = "Name cannot be blank")
	@Size(min = 2, max = 30, message = "Name must be between 1 and 30 characters")
	@Pattern(regexp = RegexPatterns.ONLY_LETTERS, message = "Name can only contain letters and spaces")
	private String name;

}
