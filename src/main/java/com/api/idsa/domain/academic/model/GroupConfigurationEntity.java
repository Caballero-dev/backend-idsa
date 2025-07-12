package com.api.idsa.domain.academic.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import com.api.idsa.domain.personnel.model.StudentEntity;
import com.api.idsa.domain.personnel.model.TutorEntity;

@Entity
@Table(
    name = "group_configurations", 
    uniqueConstraints = {
        @UniqueConstraint(
            name = "unique_group_configuration", 
            columnNames = {
                "campus_id", 
                "specialty_id", 
                "modality_id", 
                "grade_id", 
                "group_id", 
                "generation_id"
            }
        )
    }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupConfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "group_configuration_id", columnDefinition = "UUID")
    private UUID groupConfigurationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campus_id", nullable = false)
    private CampusEntity campus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialty_id", nullable = false)
    private SpecialtyEntity specialty;

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
