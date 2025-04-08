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
public class SpecialityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "speciality_id")
    private Long specialityId;

    @Column(name = "speciality_name", length = 100, nullable = false, unique = true)
    private String specialityName;

    @Column(name = "short_name", length = 10, nullable = false, unique = true)
    private String shortName;

//    @OneToMany(mappedBy = "specialty")
//    private List<GroupConfigurationEntity> groupConfigurations;

}
