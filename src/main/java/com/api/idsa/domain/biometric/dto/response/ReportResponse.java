package com.api.idsa.domain.biometric.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

import com.api.idsa.domain.personnel.dto.response.StudentResponse;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponse {

    private Long reportId;
    private StudentResponse student;
    private ZonedDateTime createdAt;
    private List<String> images;
    private Double temperature;
    private Double pupilDilationRight;
    private Double pupilDilationLeft;
    private Double heartRate;
    private Double oxygenLevels;

}
