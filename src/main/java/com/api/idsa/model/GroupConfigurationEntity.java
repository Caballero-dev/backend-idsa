package com.api.idsa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "group_configuration")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupConfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_configuration_id")
    private Long groupConfigurationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id", nullable = false)
    private CampusEntity campus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "speciality_id", nullable = false)
    private SpecialityEntity speciality;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modality_id", nullable = false)
    private ModalityEntity modality;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id", nullable = false)
    private GradeEntity grade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private GroupEntity group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "generation_id", nullable = false)
    private GenerationEntity generation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_id", nullable = false)
    private TutorEntity tutor;

    @OneToMany(mappedBy = "groupConfiguration")
    private List<StudentEntity> students;

}
