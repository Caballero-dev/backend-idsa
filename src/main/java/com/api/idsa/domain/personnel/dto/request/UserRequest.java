package com.api.idsa.domain.personnel.dto.request;

import com.api.idsa.common.util.RegexPatterns;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UserRequest", description = "Solicitud para crear o actualizar un usuario del sistema")
public class UserRequest {

	public interface PasswordUpdate extends Default {}

	@Schema(
		description = "Rol del usuario en el sistema",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotNull(message = "Role cannot be null")
	private RoleRequest role;

	@Schema(
		description = "Correo electrónico del usuario (se usará para acceso al sistema)",
		example = "usuario@example.com",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Invalid email format")
	private String email;

	@Schema(
		description = "Contraseña del usuario (opcional en actualización, se establece mediante email de verificación en creación)",
		example = "MiP@ssw0rd123",
		minLength = 8,
		maxLength = 20
	)
	// Password es opcional para actualización y en creación no es requerido
	@NotBlank(groups = PasswordUpdate.class, message = "Password cannot be blank")
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	@Pattern(regexp = RegexPatterns.PASSWORD, message = "Password contains invalid characters")
	private String password;

	@Schema(
		description = "Nombre(s) del usuario",
		example = "Pedro Luis",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 3,
		maxLength = 50
	)
	@NotBlank(message = "Name cannot be blank")
	@Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
	@Pattern(regexp = RegexPatterns.ONLY_LETTERS, message = "Name can only contain letters and spaces")
	private String name;

	@Schema(
		description = "Apellido paterno del usuario",
		example = "Ramírez",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 3,
		maxLength = 50
	)
	@NotBlank(message = "First surname cannot be blank")
	@Size(min = 3, max = 50, message = "First surname must be between 3 and 50 characters")
	@Pattern(regexp = RegexPatterns.ONLY_LETTERS, message = "First surname can only contain letters and spaces")
	private String firstSurname;

	@Schema(
		description = "Apellido materno del usuario",
		example = "González",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 3,
		maxLength = 50
	)
	@NotBlank(message = "Second surname cannot be blank")
	@Size(min = 3, max = 50, message = "Second surname must be between 3 and 50 characters")
	@Pattern(regexp = RegexPatterns.ONLY_LETTERS, message = "Second surname can only contain letters and spaces")
	private String secondSurname;

	@Schema(
		description = "Número de teléfono del usuario (10 dígitos)",
		example = "5587654321",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 10,
		maxLength = 10
	)
	@NotBlank(message = "Phone number cannot be blank")
	@Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
	@Pattern(regexp = RegexPatterns.ONLY_NUMBERS, message = "Phone number can only contain numbers")
	private String phoneNumber;

	@Schema(
		description = "Número de empleado del usuario (código único de identificación)",
		example = "USR2024001",
		requiredMode = Schema.RequiredMode.REQUIRED,
		minLength = 10,
		maxLength = 20
	)
	// Representa el numero de empleado o matricula
	@NotBlank(message = "Key cannot be blank")
	@Size(min = 10, max = 20, message = "Key must be between 10 and 20 characters")
	@Pattern(regexp = RegexPatterns.ALPHANUMERIC, message = "Key can only contain letters and numbers")
	private String key;
}
