package com.api.idsa.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {

    @NotBlank(message = "Role ID cannot be blank")
    private String roleId;

    @NotBlank(message = "Role name cannot be blank")
    private String roleName;

}
