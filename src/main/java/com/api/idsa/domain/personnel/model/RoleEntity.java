package com.api.idsa.domain.personnel.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "role_id", columnDefinition = "UUID")
    private UUID roleId;

    @Column(name = "role_name", length = 50, nullable = false, unique = true)
    private String roleName;

}
