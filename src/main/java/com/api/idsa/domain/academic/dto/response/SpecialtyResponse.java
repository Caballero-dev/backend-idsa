package com.api.idsa.domain.academic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SpecialtyResponse {

    private UUID specialtyId;

    private String name;

    private String shortName;

}
