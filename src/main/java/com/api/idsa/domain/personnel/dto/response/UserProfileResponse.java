package com.api.idsa.domain.personnel.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponse {

    private UUID userId;
    private String name;
    private String firstSurname;
    private String secondSurname;
    private String email;
    private String phone;
    private String roleName;
    private String key;
    private ZonedDateTime createdAt;

}
