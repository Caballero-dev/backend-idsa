package com.api.idsa.domain.personnel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.domain.personnel.dto.response.RoleResponse;
import com.api.idsa.domain.personnel.service.IRoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(
	name = "Roles",
	description = "Endpoints para la gestión de roles de usuario. Permite consultar los roles disponibles en el sistema."
)
@RestController
@RequestMapping("/api/admin/roles")
public class RoleController {

	@Autowired
	IRoleService roleService;

	@Operation(
		summary = "Obtener todos los roles",
		description = "Retorna la lista de todos los roles disponibles en el sistema, excluyendo el rol de ADMIN."
	)
	@GetMapping
	public ResponseEntity<ApiResponse<List<RoleResponse>>> getAllRoles() {
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<List<RoleResponse>>(
				HttpStatus.OK,
				"Roles retrieved successfully",
				roleService.getAllRoleExceptAdmin()
			)
		);
	}

}
