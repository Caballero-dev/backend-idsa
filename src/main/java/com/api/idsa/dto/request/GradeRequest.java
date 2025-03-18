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
public class GradeRequest {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 30, message = "Name must be between 1 and 30 characters")
    @Pattern(regexp = RegexPatterns.ONLY_LETTERS, message = "Name can only contain letters and spaces")
    private String name;

}
