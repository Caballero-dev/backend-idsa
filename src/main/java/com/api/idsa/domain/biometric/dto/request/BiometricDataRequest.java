package com.api.idsa.domain.biometric.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BiometricDataRequest {

    @NotBlank(message = "Student ID cannot be blank")
    private String studentId;
    
    @NotNull(message = "Temperature cannot be null")
    private BigDecimal temperature;
    
    @NotNull(message = "Heart rate cannot be null")
    private BigDecimal heartRate;
    
    @NotNull(message = "Systolic blood pressure cannot be null")
    private BigDecimal systolicBloodPressure;
    
    @NotNull(message = "Diastolic blood pressure cannot be null")
    private BigDecimal diastolicBloodPressure;
    
    @NotBlank(message = "Image cannot be blank")
    private String image;

}
