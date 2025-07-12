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
public class GroupConfigurationViewResponse {

    private UUID id;
    private String tutor;
    private String campus;
    private String specialty;
    private String specialtyShortName;
    private String modality;
    private String grade;
    private String group;
    private String generation;
    private Integer students;

}
