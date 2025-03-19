package com.api.idsa.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "people")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long personId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("person")
    private UserEntity user;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "first_surname", nullable = false, length = 50)
    private String firstSurname;

    @Column(name = "second_surname", nullable = false, length = 50)
    private String secondSurname;

    @Column(name = "phone_number", unique = true, nullable = false, length = 10)
    private String phoneNumber;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("person")
    private TutorEntity tutor;

    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("person")
    private StudentEntity student;

}
