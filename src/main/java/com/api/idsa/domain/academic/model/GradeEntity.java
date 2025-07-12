package com.api.idsa.domain.academic.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grades")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "grade_id", columnDefinition = "UUID")
    private UUID gradeId;

    @Column(name = "grade_name", length = 30, nullable = false, unique = true)
    private String gradeName;

//    @OneToMany(mappedBy = "grade")
//    private List<GroupConfigurationEntity> groupConfigurations;

}
