package com.api.idsa.domain.personnel.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TutorResponse {

    private UUID tutorId;
    private String email;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private String phoneNumber;
    private String employeeCode;

}
