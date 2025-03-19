package com.api.idsa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "reports")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    @JsonIgnoreProperties("reports")
    private StudentEntity student;

    @OneToMany(mappedBy = "report")
    private List<ReportImageEntity> images;

    @Column(name = "temperature", nullable = false, precision = 4, scale = 2)
    private BigDecimal temperature;

    @Column(name = "pupil_dilation_right", nullable = false, precision = 4, scale = 2)
    private BigDecimal pupilDilationRight;

    @Column(name = "pupil_dilation_left", nullable = false, precision = 4, scale = 2)
    private BigDecimal pupilDilationLeft;

    @Column(name = "heart_rate", nullable = false, precision = 4, scale = 2)
    private BigDecimal heartRate;

    @Column(name = "oxygen_levels", nullable = false, precision = 4, scale = 2)
    private BigDecimal oxygenLevels;

    @Column(name = "prediction_result", nullable = false, precision = 5, scale = 2)
    private BigDecimal predictionResult;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

}
