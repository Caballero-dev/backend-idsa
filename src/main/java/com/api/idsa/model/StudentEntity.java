package com.api.idsa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_configuration_id", nullable = false)
    private GroupConfigurationEntity groupConfiguration;

    @Column(name = "student_code", length = 20, nullable = false, unique = true)
    private String studentCode;

    @OneToMany(mappedBy = "student")
    private List<ReportEntity> reports;

}
