package com.api.idsa.domain.academic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(
    name = "generations",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "unique_generation",
            columnNames = {
                "start_year", 
                "end_year"
            }
        )
    }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "generation_id")
    private Long generationId;

    @Column(name = "start_year", nullable = false)
    private ZonedDateTime startYear;

    @Column(name = "end_year", nullable = false)
    private ZonedDateTime endYear;

//    @OneToMany(mappedBy = "generation")
//    private List<GroupConfigurationEntity> groupConfigurations;

}
