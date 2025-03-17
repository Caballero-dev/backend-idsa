package com.api.idsa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "modalities")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModalityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "modality_id")
    private Long modalityId;

    @Column(name = "modality_name", length = 50, nullable = false, unique = true)
    private String modalityName;

}
