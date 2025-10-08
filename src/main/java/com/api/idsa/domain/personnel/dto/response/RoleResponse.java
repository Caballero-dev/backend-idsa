package com.api.idsa.domain.personnel.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "RoleResponse", description = "Respuesta con la información de un rol de usuario")
public class RoleResponse {

	@Schema(description = "Identificador único del rol", example = "TUTOR")
	private String roleId;

	@Schema(description = "Nombre descriptivo del rol", example = "Tutor")
	private String roleName;

}
