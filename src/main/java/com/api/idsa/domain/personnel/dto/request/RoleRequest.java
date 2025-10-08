package com.api.idsa.domain.personnel.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "RoleRequest", description = "Solicitud para crear o actualizar un rol de usuario")
public class RoleRequest {

	@Schema(
		description = "Identificador único del rol (ADMIN, TUTOR)",
		example = "TUTOR",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotBlank(message = "Role ID cannot be blank")
	private String roleId;

	@Schema(
		description = "Nombre descriptivo del rol",
		example = "Tutor",
		requiredMode = Schema.RequiredMode.REQUIRED
	)
	@NotBlank(message = "Role name cannot be blank")
	private String roleName;

}
