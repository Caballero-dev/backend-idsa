package com.api.idsa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "generations")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "generation_id")
    private Long generationId;

    @Column(name = "start_year", nullable = false)
    private LocalDate startYear;

    @Column(name = "end_year", nullable = false)
    private LocalDate endYear;
}
