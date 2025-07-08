package com.api.idsa.infrastructure.model.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelPredictionResponse {
    private BigDecimal temperature;
    private BigDecimal heartRate;
    private BigDecimal systolicBloodPressure;
    private BigDecimal diastolicBloodPressure;
    private String prediction;
}