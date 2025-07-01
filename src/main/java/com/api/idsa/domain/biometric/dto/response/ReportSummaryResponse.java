package com.api.idsa.domain.biometric.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportSummaryResponse {
    
    private Integer totalStudents;
    private Integer studentsWithReports;
    private Integer studentsWithoutReports;
    private Integer studentsWithLowProbability;
    private Integer studentsWithMediumProbability;
    private Integer studentsWithHighProbability;

}
