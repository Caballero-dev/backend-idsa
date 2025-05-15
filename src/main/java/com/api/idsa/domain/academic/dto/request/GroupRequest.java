package com.api.idsa.domain.academic.dto.request;

import com.api.idsa.common.util.RegexPatterns;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequest {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 2, message = "Name must be between 1 and 2 characters")
    @Pattern(regexp = RegexPatterns.ONLY_PLAIN_LETTERS, message = "Name can only contain letters")
    private String name;

}
