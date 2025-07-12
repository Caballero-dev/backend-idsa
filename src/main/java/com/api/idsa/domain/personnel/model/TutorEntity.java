package com.api.idsa.domain.personnel.model;

import com.api.idsa.domain.academic.model.GroupConfigurationEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tutors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TutorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "tutor_id", columnDefinition = "UUID")
    private UUID tutorId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity person;

    @Column(name = "employee_code", unique = true, nullable = false, length = 20)
    private String employeeCode;

    @OneToMany(mappedBy = "tutor")
    private List<GroupConfigurationEntity> groupConfigurations;

}
