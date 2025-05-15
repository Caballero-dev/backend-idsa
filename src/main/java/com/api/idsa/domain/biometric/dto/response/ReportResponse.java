package com.api.idsa.domain.biometric.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
    private BigDecimal temperature;
    private BigDecimal heartRate;
    private BigDecimal systolicBloodPressure;
    private BigDecimal diastolicBloodPressure;
    private BigDecimal pupilDilationRight;
    private BigDecimal pupilDilationLeft;
    private List<String> images;
    private ZonedDateTime createdAt;

}
