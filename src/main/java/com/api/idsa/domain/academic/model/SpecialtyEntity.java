package com.api.idsa.domain.academic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "specialties")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecialtyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "specialty_id")
    private Long specialtyId;

    @Column(name = "specialty_name", length = 100, nullable = false, unique = true)
    private String specialtyName;

    @Column(name = "short_name", length = 10, nullable = false, unique = true)
    private String shortName;

//    @OneToMany(mappedBy = "specialty")
//    private List<GroupConfigurationEntity> groupConfigurations;

}
