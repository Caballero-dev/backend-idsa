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
@Schema(name = "GroupRequest", description = "Solicitud para crear o actualizar un grupo escolar")
public class GroupRequest {

	@Schema(
		description = "Identificador del grupo (A, B, C, etc.)",
		example = "A",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 1,
		maxLength = 2
	)
	@NotBlank(message = "Name cannot be blank")
	@Size(min = 1, max = 2, message = "Name must be between 1 and 2 characters")
	@Pattern(regexp = RegexPatterns.ONLY_PLAIN_LETTERS, message = "Name can only contain letters")
	private String name;

}
