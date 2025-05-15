package com.api.idsa.domain.biometric.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import com.api.idsa.domain.personnel.model.StudentEntity;

@Entity
@Table(name = "reports")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity student;

    @Column(name = "temperature", nullable = false, precision = 4, scale = 2)
    private BigDecimal temperature;

    @Column(name = "heart_rate", nullable = false, precision = 4, scale = 2)
    private BigDecimal heartRate;

    @Column(name = "systolic_blood_pressure", nullable = false, precision = 5, scale = 2)
    private BigDecimal systolicBloodPressure;

    @Column(name = "diastolic_blood_pressure", nullable = false, precision = 5, scale = 2)
    private BigDecimal diastolicBloodPressure;

    @Column(name = "pupil_dilation_right", nullable = false, precision = 4, scale = 2)
    private BigDecimal pupilDilationRight;

    @Column(name = "pupil_dilation_left", nullable = false, precision = 4, scale = 2)
    private BigDecimal pupilDilationLeft;

    @Column(name = "prediction_result", nullable = false, precision = 5, scale = 2)
    private BigDecimal predictionResult;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "report_biometric_data",
            joinColumns = @JoinColumn(name = "report_id"),
            inverseJoinColumns = @JoinColumn(name = "biometric_data_id"))
    private List<BiometricDataEntity> biometricData;

}
