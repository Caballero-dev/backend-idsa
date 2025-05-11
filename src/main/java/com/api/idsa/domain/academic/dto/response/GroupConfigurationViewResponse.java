package com.api.idsa.domain.academic.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupConfigurationViewResponse {

    private Long id;
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
