package com.api.idsa.domain.academic.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.academic.dto.request.GroupRequest;
import com.api.idsa.domain.academic.dto.response.GroupResponse;
import com.api.idsa.domain.academic.service.IGroupService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/groups")
@Tag(
	name = "Grupos",
	description = "Endpoints para la gestión de grupos escolares. Permite crear, consultar, actualizar y eliminar grupos."
)
public class GroupController {

	@Autowired
	IGroupService groupService;

	@Operation(
		summary = "Obtener todos los grupos",
		description = "Retorna una lista paginada de todos los grupos escolares registrados. " +
		              "Permite búsqueda por identificador del grupo."
	)
	@GetMapping
	public ResponseEntity<ApiResponse<List<GroupResponse>>> getAllGroups(
		@Parameter(description = "Número de página (inicia en 0)") @RequestParam(defaultValue = "0") int page,
		@Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "100") int size,
		@Parameter(description = "Término de búsqueda para filtrar por identificador") @RequestParam(required = false) String search
	) {
		Pageable pageable = Pageable.ofSize(size).withPage(page);
		Page<GroupResponse> groupPage = groupService.getAllGroup(pageable, search);
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<List<GroupResponse>>(
				HttpStatus.OK,
				"Groups retrieved successfully",
				groupPage.getContent(),
				PageInfo.fromPage(groupPage)
			)
		);
	}

	@Operation(
		summary = "Crear un nuevo grupo",
		description = "Crea un nuevo grupo escolar con la información proporcionada. " +
		              "El identificador del grupo debe ser único (A, B, C, etc.)."
	)
	@PostMapping
	public ResponseEntity<ApiResponse<GroupResponse>> createGroup(@Valid @RequestBody GroupRequest groupRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
			new ApiResponse<GroupResponse>(
				HttpStatus.CREATED,
				"Group created successfully",
				groupService.createGroup(groupRequest)
			)
		);
	}

	@Operation(
		summary = "Actualizar un grupo",
		description = "Actualiza la información de un grupo escolar existente por su ID. " +
		              "El grupo debe existir en el sistema."
	)
	@PutMapping("/{groupId}")
	public ResponseEntity<ApiResponse<GroupResponse>> updateGroup(
		@Parameter(description = "ID único del grupo a actualizar") @PathVariable UUID groupId,
		@Valid @RequestBody GroupRequest groupRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<GroupResponse>(
				HttpStatus.OK,
				"Group updated successfully",
				groupService.updateGroup(groupId, groupRequest)
			)
		);
	}

	@Operation(
		summary = "Eliminar un grupo",
		description = "Elimina un grupo escolar del sistema por su ID. " +
		              "El grupo no debe tener configuraciones de grupo asociadas."
	)
	@DeleteMapping("/{groupId}")
	public void deleteGroup(
		@Parameter(description = "ID único del grupo a eliminar") @PathVariable UUID groupId) {
		groupService.deleteGroup(groupId);
	}

}
