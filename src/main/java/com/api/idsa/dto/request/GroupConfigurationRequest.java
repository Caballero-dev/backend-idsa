package com.api.idsa.dto.request;

import com.api.idsa.dto.response.*;
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

    @NotNull(message = "Speciality cannot be null")
    private SpecialityResponse speciality;

    @NotNull(message = "Modality cannot be null")
    private ModalityResponse modality;

    @NotNull(message = "Grade cannot be null")
    private GradeResponse grade;

    @NotNull(message = "Group cannot be null")
    private GroupResponse group;

    @NotNull(message = "Generation cannot be null")
    private GenerationResponse generation;

}
