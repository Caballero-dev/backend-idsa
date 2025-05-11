package com.api.idsa.domain.academic.dto.request;

import com.api.idsa.domain.academic.dto.response.CampusResponse;
import com.api.idsa.domain.academic.dto.response.GenerationResponse;
import com.api.idsa.domain.academic.dto.response.GradeResponse;
import com.api.idsa.domain.academic.dto.response.GroupResponse;
import com.api.idsa.domain.academic.dto.response.ModalityResponse;
import com.api.idsa.domain.academic.dto.response.SpecialtyResponse;
import com.api.idsa.domain.personnel.dto.response.TutorResponse;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupConfigurationRequest {

    @NotNull(message = "Tutor cannot be null")
    private TutorResponse tutor;

    @NotNull(message = "Campus cannot be null")
    private CampusResponse campus;

    @NotNull(message = "Specialty cannot be null")
    private SpecialtyResponse specialty;

    @NotNull(message = "Modality cannot be null")
    private ModalityResponse modality;

    @NotNull(message = "Grade cannot be null")
    private GradeResponse grade;

    @NotNull(message = "Group cannot be null")
    private GroupResponse group;

    @NotNull(message = "Generation cannot be null")
    private GenerationResponse generation;

}
