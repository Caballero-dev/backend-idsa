package com.api.idsa.domain.personnel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.api.idsa.domain.academic.model.GroupConfigurationEntity;
import com.api.idsa.domain.biometric.model.BiometricDataEntity;
import com.api.idsa.domain.biometric.model.ReportEntity;

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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_configuration_id", nullable = false)
    private GroupConfigurationEntity groupConfiguration;

    @Column(name = "student_code", length = 20, nullable = false, unique = true)
    private String studentCode;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<BiometricDataEntity> biometricData;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<ReportEntity> reports;

}
