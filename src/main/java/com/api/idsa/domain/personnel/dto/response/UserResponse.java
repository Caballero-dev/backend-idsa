package com.api.idsa.domain.personnel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long userId;
    private String email;
    private RoleResponse role;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private String key;
    private String phoneNumber;
    private ZonedDateTime createdAt;
    private Boolean isActive;

}
