package com.api.idsa.domain.academic.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

import com.api.idsa.common.validation.annotation.YearRange;

@Data
@AllArgsConstructor
@NoArgsConstructor
@YearRange
public class GenerationRequest {

    @NotNull(message = "Start year is required")
    private ZonedDateTime yearStart;

    @NotNull(message = "End year is required")
    private ZonedDateTime yearEnd;
}
