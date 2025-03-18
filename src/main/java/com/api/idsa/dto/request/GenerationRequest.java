package com.api.idsa.dto.request;

import com.api.idsa.validation.YearRange;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@YearRange
public class GenerationRequest {

    @NotNull(message = "Start year is required")
    private LocalDate yearStart;

    @NotNull(message = "End year is required")
    private LocalDate yearEnd;
}
