package com.api.idsa.domain.academic.model;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "group_id", columnDefinition = "UUID")
    private UUID groupId;

    @Column(name = "group_name", length = 2, nullable = false, unique = true)
    private String groupName;

//    @OneToMany(mappedBy = "group")
//    private List<GroupConfigurationEntity> groupConfigurations;

}
