package com.api.idsa.domain.academic.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.academic.dto.request.GroupConfigurationRequest;
import com.api.idsa.domain.academic.dto.response.GroupConfigurationResponse;
import com.api.idsa.domain.academic.service.IGroupConfigurationService;

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
@RequestMapping("/api/admin/group-configurations")
@Tag(
	name = "Configuraciones de Grupo",
	description = "Endpoints para la gestión de configuraciones de grupos académicos. " +
	              "Permite crear, consultar, actualizar y eliminar configuraciones completas de grupos " +
	              "(tutor, plantel, especialidad, modalidad, grado, grupo y generación)."
)
public class GroupConfigurationController {

	@Autowired
	IGroupConfigurationService groupConfigurationService;

	@Operation(
		summary = "Obtener todas las configuraciones de grupo",
		description = "Retorna una lista paginada de todas las configuraciones de grupos académicos. " +
		              "Permite búsqueda por cualquier campo de la configuración."
	)
	@GetMapping
	public ResponseEntity<ApiResponse<List<GroupConfigurationResponse>>> getAllGroupConfigurations(
		@Parameter(description = "Número de página (inicia en 0)") @RequestParam(defaultValue = "0") int page,
		@Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "100") int size,
		@Parameter(description = "Término de búsqueda general") @RequestParam(required = false) String search
	) {
		Pageable pageable = Pageable.ofSize(size).withPage(page);
		Page<GroupConfigurationResponse> groupConfigPage = groupConfigurationService.getAllGroupConfiguration(pageable, search);
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<List<GroupConfigurationResponse>>(
				HttpStatus.OK,
				"Group configurations retrieved successfully",
				groupConfigPage.getContent(),
				PageInfo.fromPage(groupConfigPage)
			)
		);
	}

	@Operation(
		summary = "Crear una nueva configuración de grupo",
		description = "Crea una nueva configuración de grupo académico completa. " +
		              "Requiere especificar tutor, plantel, especialidad, modalidad, grado, grupo y generación. " +
		              "La combinación de estos elementos debe ser única."
	)
	@PostMapping
	public ResponseEntity<ApiResponse<GroupConfigurationResponse>> createGroupConfiguration(@Valid @RequestBody GroupConfigurationRequest groupConfigurationRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
			new ApiResponse<GroupConfigurationResponse>(
				HttpStatus.CREATED,
				"Group configuration created successfully",
				groupConfigurationService.createGroupConfiguration(groupConfigurationRequest)
			)
		);
	}

	@Operation(
		summary = "Actualizar una configuración de grupo",
		description = "Actualiza la información de una configuración de grupo existente por su ID. " +
		              "La configuración debe existir en el sistema."
	)
	@PutMapping("/{groupConfigurationId}")
	public ResponseEntity<ApiResponse<GroupConfigurationResponse>> updateGroupConfiguration(
		@Parameter(description = "ID único de la configuración a actualizar") @PathVariable UUID groupConfigurationId,
		@Valid @RequestBody GroupConfigurationRequest groupConfigurationRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<GroupConfigurationResponse>(
				HttpStatus.OK,
				"Group configuration updated successfully",
				groupConfigurationService.updateGroupConfiguration(groupConfigurationId, groupConfigurationRequest)
			)
		);
	}

	@Operation(
		summary = "Eliminar una configuración de grupo",
		description = "Elimina una configuración de grupo del sistema por su ID. " +
		              "La configuración no debe tener estudiantes asociados."
	)
	@DeleteMapping("/{groupConfigurationId}")
	public void deleteGroupConfiguration(
		@Parameter(description = "ID único de la configuración a eliminar") @PathVariable UUID groupConfigurationId) {
		groupConfigurationService.deleteGroupConfiguration(groupConfigurationId);
	}

}
