package com.api.idsa.security.dto.request;

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
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "LoginRequest", description = "Credenciales de inicio de sesión del usuario (email y contraseña)")
public class LoginRequest {

    @Schema(
        description = "Dirección de correo electrónico del usuario",
        example = "usuario@example.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(
        description = "Contraseña del usuario (entre 8 y 20 caracteres)",
        example = "MiP@ssw0rd",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(regexp = RegexPatterns.PASSWORD, message = "Password contains invalid characters")
    private String password;

}
