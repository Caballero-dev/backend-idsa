package com.api.idsa.domain.academic.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.academic.dto.request.SpecialtyRequest;
import com.api.idsa.domain.academic.dto.response.SpecialtyResponse;
import com.api.idsa.domain.academic.service.ISpecialtyService;

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
@RequestMapping("/api/admin/specialities")
@Tag(
	name = "Especialidades",
	description = "Endpoints para la gestión de especialidades técnicas. Permite crear, consultar, actualizar y eliminar especialidades."
)
public class SpecialtyController {

	@Autowired
	ISpecialtyService specialtyService;

	@Operation(
		summary = "Obtener todas las especialidades",
		description = "Retorna una lista paginada de todas las especialidades técnicas registradas. " +
		              "Permite búsqueda por nombre de especialidad."
	)
	@GetMapping
	public ResponseEntity<ApiResponse<List<SpecialtyResponse>>> getAllSpecialty(
		@Parameter(description = "Número de página (inicia en 0)") @RequestParam(defaultValue = "0") int page,
		@Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "100") int size,
		@Parameter(description = "Término de búsqueda para filtrar por nombre") @RequestParam(required = false) String search
	) {
		Pageable pageable = Pageable.ofSize(size).withPage(page);
		Page<SpecialtyResponse> specialtyPage = specialtyService.getAllSpecialty(pageable, search);
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<List<SpecialtyResponse>>(
				HttpStatus.OK,
				"Specialties retrieved successfully",
				specialtyPage.getContent(),
				PageInfo.fromPage(specialtyPage)
			)
		);
	}

	@Operation(
		summary = "Crear una nueva especialidad",
		description = "Crea una nueva especialidad técnica con la información proporcionada. " +
		              "El nombre y nombre corto de la especialidad deben ser únicos."
	)
	@PostMapping
	public ResponseEntity<ApiResponse<SpecialtyResponse>> createSpecialty(@Valid @RequestBody SpecialtyRequest specialtyRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
			new ApiResponse<SpecialtyResponse>(
				HttpStatus.CREATED,
				"Specialty created successfully",
				specialtyService.createSpecialty(specialtyRequest)
			)
		);
	}

	@Operation(
		summary = "Actualizar una especialidad",
		description = "Actualiza la información de una especialidad técnica existente por su ID. " +
		              "La especialidad debe existir en el sistema."
	)
	@PutMapping("/{specialtyId}")
	public ResponseEntity<ApiResponse<SpecialtyResponse>> updateSpecialty(
		@Parameter(description = "ID único de la especialidad a actualizar") @PathVariable UUID specialtyId,
		@Valid @RequestBody SpecialtyRequest specialtyRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<SpecialtyResponse>(
				HttpStatus.OK,
				"Specialty updated successfully",
				specialtyService.updateSpecialty(specialtyId, specialtyRequest)
			)
		);
	}

	@Operation(
		summary = "Eliminar una especialidad",
		description = "Elimina una especialidad técnica del sistema por su ID. " +
		              "La especialidad no debe tener configuraciones de grupo asociadas."
	)
	@DeleteMapping("/{specialtyId}")
	public void deleteSpeciality(
		@Parameter(description = "ID único de la especialidad a eliminar") @PathVariable UUID specialtyId) {
		specialtyService.deleteSpecialty(specialtyId);
	}

}
