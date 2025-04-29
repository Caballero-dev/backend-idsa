package com.api.idsa.infrastructure.mqtt.dto.requets;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorData {

    private Long student_id;
    private BigDecimal temperature;
    private BigDecimal heart_rate;
    private BigDecimal systolic_blood_pressure;
    private BigDecimal diastolic_blood_pressure;
    private String image;

}