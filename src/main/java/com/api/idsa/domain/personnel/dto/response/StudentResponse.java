package com.api.idsa.domain.personnel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import com.api.idsa.domain.biometric.enums.PredictionLevel;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {

    private UUID studentId;
    private String studentCode;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private String phoneNumber;
    private PredictionLevel predictionResult;

}
