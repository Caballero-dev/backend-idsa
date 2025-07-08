package com.api.idsa.infrastructure.model.dto.request;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelPredictionRequest {

    private BigDecimal temperature;
    private BigDecimal heartRate;
    private BigDecimal systolicBloodPressure;
    private BigDecimal diastolicBloodPressure;
    private String imagePath;
    private ZonedDateTime createdAt;

}