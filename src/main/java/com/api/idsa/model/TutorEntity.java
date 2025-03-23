package com.api.idsa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tutors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TutorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tutor_id")
    private Long tutorId;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity person;

    @Column(name = "employee_code", unique = true, nullable = false, length = 20)
    private String employeeCode;

    @OneToMany(mappedBy = "tutor")
    @JsonIgnoreProperties("tutor")
    private List<GroupConfigurationEntity> groupConfigurations;

}
