package com.api.idsa.domain.academic.dto.response;

import com.api.idsa.domain.personnel.dto.response.TutorResponse;

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

    private SpecialtyResponse specialty;

    private ModalityResponse modality;

    private GradeResponse grade;

    private GroupResponse group;

    private GenerationResponse generation;

}
