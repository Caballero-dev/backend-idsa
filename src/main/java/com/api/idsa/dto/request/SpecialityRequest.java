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
public class SpecialityRequest {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    @Pattern(regexp = RegexPatterns.ONLY_LETTERS, message = "Name can only contain letters and spaces")
    private String name;

    @NotBlank(message = "Short name cannot be blank")
    @Size(min = 2, max = 10, message = "Short name must be between 2 and 10 characters")
    @Pattern(regexp = RegexPatterns.ONLY_PLAIN_LETTERS, message = "Short name can only contain letters")
    private String shortName;

}
