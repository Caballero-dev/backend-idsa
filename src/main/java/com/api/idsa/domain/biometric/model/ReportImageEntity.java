package com.api.idsa.domain.biometric.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "report_images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "report_id")
    private ReportEntity report;

    @Column(name = "image_path", nullable = false)
    private String imagePath;

}
