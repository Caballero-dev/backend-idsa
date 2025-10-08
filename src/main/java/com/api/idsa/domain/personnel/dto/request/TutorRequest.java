package com.api.idsa.domain.personnel.dto.request;

import com.api.idsa.common.util.RegexPatterns;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "TutorRequest", description = "Solicitud para crear o actualizar un tutor")
public class TutorRequest {

	@Schema(
		description = "Correo electrónico del tutor (se usará para acceso al sistema)",
		example = "tutor@example.com",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Invalid email format")
	private String email;

	@Schema(
		description = "Nombre(s) del tutor",
		example = "María Elena",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 3,
		maxLength = 50
	)
	@NotBlank(message = "Name cannot be blank")
	@Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
	@Pattern(regexp = RegexPatterns.ONLY_LETTERS, message = "Name can only contain letters and spaces")
	private String name;

	@Schema(
		description = "Apellido paterno del tutor",
		example = "Pérez",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 3,
		maxLength = 50
	)
	@NotBlank(message = "First surname cannot be blank")
	@Size(min = 3, max = 50, message = "First surname must be between 3 and 50 characters")
	@Pattern(regexp = RegexPatterns.ONLY_LETTERS, message = "First surname can only contain letters and spaces")
	private String firstSurname;

	@Schema(
		description = "Apellido materno del tutor",
		example = "Martínez",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 3,
		maxLength = 50
	)
	@NotBlank(message = "Second surname cannot be blank")
	@Size(min = 3, max = 50, message = "Second surname must be between 3 and 50 characters")
	@Pattern(regexp = RegexPatterns.ONLY_LETTERS, message = "Second surname can only contain letters and spaces")
	private String secondSurname;

	@Schema(
		description = "Número de teléfono del tutor (10 dígitos)",
		example = "5598765432",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 10,
		maxLength = 10
	)
	@NotBlank(message = "Phone number cannot be blank")
	@Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
	@Pattern(regexp = RegexPatterns.ONLY_NUMBERS, message = "Phone number can only contain numbers")
	private String phoneNumber;

	@Schema(
		description = "Número de empleado del tutor (código único de identificación)",
		example = "EMP2024001",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 10,
		maxLength = 20
	)
	@NotBlank(message = "Key cannot be blank")
	@Size(min = 10, max = 20, message = "Key must be between 10 and 20 characters")
	@Pattern(regexp = RegexPatterns.ALPHANUMERIC, message = "Key can only contain letters and numbers")
	private String employeeCode;

}
