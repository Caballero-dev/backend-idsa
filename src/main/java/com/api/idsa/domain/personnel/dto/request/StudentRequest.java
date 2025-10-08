package com.api.idsa.domain.personnel.dto.request;

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
@Schema(name = "StudentRequest", description = "Solicitud para crear o actualizar un estudiante")
public class StudentRequest {

	@Schema(
		description = "Matrícula del estudiante (código único de identificación)",
		example = "2024001234",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 10,
		maxLength = 20
	)
	@NotBlank(message = "Student code cannot be blank")
	@Size(min = 10, max = 20, message = "Student code must be between 10 and 20 characters")
	@Pattern(regexp = RegexPatterns.ALPHANUMERIC, message = "Student code can only contain letters and numbers")
	private String studentCode;

	@Schema(
		description = "Nombre(s) del estudiante",
		example = "Juan Carlos",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 3,
		maxLength = 50
	)
	@NotBlank(message = "Name cannot be blank")
	@Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
	@Pattern(regexp = RegexPatterns.ONLY_LETTERS, message = "Name can only contain letters and spaces")
	private String name;

	@Schema(
		description = "Apellido paterno del estudiante",
		example = "García",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 3,
		maxLength = 50
	)
	@NotBlank(message = "First surname cannot be blank")
	@Size(min = 3, max = 50, message = "First surname must be between 3 and 50 characters")
	@Pattern(regexp = RegexPatterns.ONLY_LETTERS, message = "First surname can only contain letters and spaces")
	private String firstSurname;

	@Schema(
		description = "Apellido materno del estudiante",
		example = "López",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 3,
		maxLength = 50
	)
	@NotBlank(message = "Second surname cannot be blank")
	@Size(min = 3, max = 50, message = "Second surname must be between 3 and 50 characters")
	@Pattern(regexp = RegexPatterns.ONLY_LETTERS, message = "Second surname can only contain letters and spaces")
	private String secondSurname;

	@Schema(
		description = "Número de teléfono del estudiante (10 dígitos)",
		example = "5512345678",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 10,
		maxLength = 10
	)
	@NotBlank(message = "Phone number cannot be blank")
	@Size(min = 10, max = 10, message = "Phone number must be 10 digits")
	@Pattern(regexp = RegexPatterns.ONLY_NUMBERS, message = "Phone number can only contain numbers")
	private String phoneNumber;

//    @NotBlank(message = "Email cannot be blank")
//    @Size(min = 6, max = 100, message = "Email must be between 6 and 100 characters")
//    @Email(message = "Invalid email format")
//    private String email;

}
