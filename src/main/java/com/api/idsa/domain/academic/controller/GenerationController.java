package com.api.idsa.domain.academic.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.academic.dto.request.GenerationRequest;
import com.api.idsa.domain.academic.dto.response.GenerationResponse;
import com.api.idsa.domain.academic.service.IGenerationService;

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
@RequestMapping("/api/admin/generations")
@Tag(
	name = "Generaciones",
	description = "Endpoints para la gestión de generaciones académicas. Permite crear, consultar, actualizar y eliminar generaciones."
)
public class GenerationController {

	@Autowired
	IGenerationService generationService;

	@Operation(
		summary = "Obtener todas las generaciones",
		description = "Retorna una lista paginada de todas las generaciones académicas registradas. " +
		              "Permite búsqueda por rango de años."
	)
	@GetMapping
	public ResponseEntity<ApiResponse<List<GenerationResponse>>> getAllGeneration(
		@Parameter(description = "Número de página (inicia en 0)") @RequestParam(defaultValue = "0") int page,
		@Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "100") int size,
		@Parameter(description = "Término de búsqueda para filtrar por año") @RequestParam(required = false) String search
	) {
		Pageable pageable = Pageable.ofSize(size).withPage(page);
		Page<GenerationResponse> generationPage = generationService.getAllGeneration(pageable, search);
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<List<GenerationResponse>>(
				HttpStatus.OK,
				"Generations retrieved successfully",
				generationPage.getContent(),
				PageInfo.fromPage(generationPage)
			)
		);
	}

	@Operation(
		summary = "Crear una nueva generación",
		description = "Crea una nueva generación académica con el rango de años proporcionado. " +
		              "El año de inicio debe ser menor al año de fin."
	)
	@PostMapping
	public ResponseEntity<ApiResponse<GenerationResponse>> createGeneration(@Valid @RequestBody GenerationRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
			new ApiResponse<GenerationResponse>(
				HttpStatus.CREATED,
				"Generation created successfully",
				generationService.createGeneration(request)
			)
		);
	}

	@Operation(
		summary = "Actualizar una generación",
		description = "Actualiza la información de una generación académica existente por su ID. " +
		              "La generación debe existir en el sistema."
	)
	@PutMapping("/{generationId}")
	public ResponseEntity<ApiResponse<GenerationResponse>> updateGeneration(
		@Parameter(description = "ID único de la generación a actualizar") @PathVariable UUID generationId,
		@Valid @RequestBody GenerationRequest generationRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<GenerationResponse>(
				HttpStatus.OK,
				"Generation updated successfully",
				generationService.updateGeneration(generationId, generationRequest)
			)
		);
	}

	@Operation(
		summary = "Eliminar una generación",
		description = "Elimina una generación académica del sistema por su ID. " +
		              "La generación no debe tener configuraciones de grupo asociadas."
	)
	@DeleteMapping("/{generationId}")
	public void deleteGeneration(
		@Parameter(description = "ID único de la generación a eliminar") @PathVariable UUID generationId) {
		generationService.deleteGeneration(generationId);
	}
}
