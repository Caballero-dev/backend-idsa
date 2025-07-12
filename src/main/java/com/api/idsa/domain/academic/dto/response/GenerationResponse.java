package com.api.idsa.domain.academic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerationResponse {

    private UUID generationId;

    private ZonedDateTime yearStart;

    private ZonedDateTime yearEnd;

}
