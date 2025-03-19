package com.api.idsa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "campus_id", nullable = false)
    @JsonIgnoreProperties("groupConfigurations")
    private CampusEntity campus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "speciality_id", nullable = false)
    @JsonIgnoreProperties("groupConfigurations")
    private SpecialityEntity speciality;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "modality_id", nullable = false)
    @JsonIgnoreProperties("groupConfigurations")
    private ModalityEntity modality;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grade_id", nullable = false)
    @JsonIgnoreProperties("groupConfigurations")
    private GradeEntity grade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id", nullable = false)
    @JsonIgnoreProperties("groupConfigurations")
    private GroupEntity group;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "generation_id", nullable = false)
    @JsonIgnoreProperties("groupConfigurations")
    private GenerationEntity generation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tutor_id", nullable = false)
    @JsonIgnoreProperties("groupConfigurations")
    private TutorEntity tutor;

    @JsonIgnore
    @OneToMany(mappedBy = "groupConfiguration")
    @JsonIgnoreProperties("groupConfigurations")
    private List<StudentEntity> students;

}
