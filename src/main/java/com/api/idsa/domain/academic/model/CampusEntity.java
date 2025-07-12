package com.api.idsa.domain.academic.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "campuses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "campus_id", columnDefinition = "UUID")
    private UUID campusId;

    @Column(name = "campus_name", length = 100, nullable = false, unique = true)
    private String campusName;

//    @OneToMany(mappedBy = "campus")
//    private List<GroupConfigurationEntity> groupConfigurations;

}
