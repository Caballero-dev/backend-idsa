package com.api.idsa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "report_images")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReportImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private ReportEntity report;

    @Column(name = "image_path", nullable = false)
    private String imagePath;

}
