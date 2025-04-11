package com.api.idsa.domain.biometric.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import com.api.idsa.domain.personnel.model.StudentEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "biometric_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BiometricDataEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "biometric_data_id")
    private Long biometricDataId;

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

    @Column(name = "image_path", nullable = false, length = 255)
    private String imagePath;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @ManyToMany(mappedBy = "biometricData")
    private List<ReportEntity> reports;

}
