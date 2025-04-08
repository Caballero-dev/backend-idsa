package com.api.idsa.domain.personnel.dto.request;

import com.api.idsa.common.util.RegexPatterns;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @NotNull(message = "Role cannot be null")
    private RoleRequest role;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    // Password es opcional para actualización
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Pattern(regexp = RegexPatterns.PASSWORD, message = "Password contains invalid characters")
    private String password;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Pattern(regexp = RegexPatterns.ONLY_LETTERS, message = "Name can only contain letters and spaces")
    private String name;

    @NotBlank(message = "First surname cannot be blank")
    @Size(min = 3, max = 50, message = "First surname must be between 3 and 50 characters")
    @Pattern(regexp = RegexPatterns.ONLY_LETTERS, message = "First surname can only contain letters and spaces")
    private String firstSurname;

    @NotBlank(message = "Second surname cannot be blank")
    @Size(min = 3, max = 50, message = "Second surname must be between 3 and 50 characters")
    @Pattern(regexp = RegexPatterns.ONLY_LETTERS, message = "Second surname can only contain letters and spaces")
    private String secondSurname;

    @NotBlank(message = "Phone number cannot be blank")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    @Pattern(regexp = RegexPatterns.ONLY_NUMBERS, message = "Phone number can only contain numbers")
    private String phoneNumber;

    @NotBlank(message = "Key cannot be blank")
    @Size(min = 10, max = 20, message = "Key must be between 10 and 20 characters")
    @Pattern(regexp = RegexPatterns.ALPHANUMERIC, message = "Key can only contain letters and numbers")
    private String key;
}
