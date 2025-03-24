package com.api.idsa.dto.request;

import com.api.idsa.util.RegexPatterns;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {

    @NotBlank(message = "Student code cannot be blank")
    @Size(min = 10, max = 20, message = "Student code must be between 10 and 20 characters")
    @Pattern(regexp = RegexPatterns.ALPHANUMERIC, message = "Student code can only contain letters and numbers")
    private String studentCode;

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
    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    @Pattern(regexp = RegexPatterns.ONLY_NUMBERS, message = "Phone number can only contain numbers")
    private String phoneNumber;

//    @NotBlank(message = "Email cannot be blank")
//    @Size(min = 6, max = 100, message = "Email must be between 6 and 100 characters")
//    @Email(message = "Invalid email format")
//    private String email;

}
