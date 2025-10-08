package com.api.idsa.domain.academic.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.academic.dto.request.ModalityRequest;
import com.api.idsa.domain.academic.dto.response.ModalityResponse;
import com.api.idsa.domain.academic.service.IModalityService;

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
@RequestMapping("/api/admin/modalities")
@Tag(
	name = "Modalidades",
	description = "Endpoints para la gestión de modalidades educativas. Permite crear, consultar, actualizar y eliminar modalidades."
)
public class ModalityController {

	@Autowired
	IModalityService modalityService;

	@Operation(
		summary = "Obtener todas las modalidades",
		description = "Retorna una lista paginada de todas las modalidades educativas registradas. " +
		              "Permite búsqueda por nombre de modalidad."
	)
	@GetMapping
	public ResponseEntity<ApiResponse<List<ModalityResponse>>> getAllModality(
		@Parameter(description = "Número de página (inicia en 0)") @RequestParam(defaultValue = "0") int page,
		@Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "100") int size,
		@Parameter(description = "Término de búsqueda para filtrar por nombre") @RequestParam(required = false) String search
	) {
		Pageable pageable = Pageable.ofSize(size).withPage(page);
		Page<ModalityResponse> modalityPage = modalityService.getAllModality(pageable, search);
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<List<ModalityResponse>>(
				HttpStatus.OK,
				"Modalities retrieved successfully",
				modalityPage.getContent(),
				PageInfo.fromPage(modalityPage)
			)
		);
	}

	@Operation(
		summary = "Crear una nueva modalidad",
		description = "Crea una nueva modalidad educativa con la información proporcionada. " +
		              "El nombre de la modalidad debe ser único."
	)
	@PostMapping
	public ResponseEntity<ApiResponse<ModalityResponse>> createModality(@Valid @RequestBody ModalityRequest modalityRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
			new ApiResponse<ModalityResponse>(
				HttpStatus.CREATED,
				"Modality created successfully",
				modalityService.createModality(modalityRequest)
			)
		);
	}

	@Operation(
		summary = "Actualizar una modalidad",
		description = "Actualiza la información de una modalidad educativa existente por su ID. " +
		              "La modalidad debe existir en el sistema."
	)
	@PutMapping("/{modalityId}")
	public ResponseEntity<ApiResponse<ModalityResponse>> updateModality(
		@Parameter(description = "ID único de la modalidad a actualizar") @PathVariable UUID modalityId,
		@Valid @RequestBody ModalityRequest modalityRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<ModalityResponse>(
				HttpStatus.OK,
				"Modality updated successfully",
				modalityService.updateModality(modalityId, modalityRequest)
			)
		);
	}

	@Operation(
		summary = "Eliminar una modalidad",
		description = "Elimina una modalidad educativa del sistema por su ID. " +
		              "La modalidad no debe tener configuraciones de grupo asociadas."
	)
	@DeleteMapping("/{modalityId}")
	public void deleteModality(
		@Parameter(description = "ID único de la modalidad a eliminar") @PathVariable UUID modalityId) {
		modalityService.deleteModality(modalityId);
	}

}
