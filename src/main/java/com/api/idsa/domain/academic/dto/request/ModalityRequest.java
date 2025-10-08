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
@Schema(name = "ModalityRequest", description = "Solicitud para crear o actualizar una modalidad educativa")
public class ModalityRequest {

	@Schema(
		description = "Nombre de la modalidad educativa (presencial, mixta, en línea, etc.)",
		example = "Escolarizada",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 3,
		maxLength = 50
	)
	@NotBlank(message = "Name cannot be blank")
	@Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
	@Pattern(regexp = RegexPatterns.ONLY_LETTERS, message = "Name can only contain letters and spaces")
	private String name;

}
