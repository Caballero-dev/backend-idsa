package com.api.idsa.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupConfigurationResponse {

    private Long groupConfigurationId;

    private TutorResponse tutor;

    private CampusResponse campus;

    private SpecialityResponse speciality;

    private ModalityResponse modality;

    private GradeResponse grade;

    private GroupResponse group;

    private GenerationResponse generation;

}
