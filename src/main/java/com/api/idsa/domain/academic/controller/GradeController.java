package com.api.idsa.domain.academic.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.academic.dto.request.GradeRequest;
import com.api.idsa.domain.academic.dto.response.GradeResponse;
import com.api.idsa.domain.academic.service.IGradeService;

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
@RequestMapping("/api/admin/grades")
@Tag(
	name = "Grados",
	description = "Endpoints para la gestión de grados escolares. Permite crear, consultar, actualizar y eliminar grados."
)
public class GradeController {

	@Autowired
	IGradeService gradeService;

	@Operation(
		summary = "Obtener todos los grados",
		description = "Retorna una lista paginada de todos los grados escolares registrados. " +
		              "Permite búsqueda por nombre del grado."
	)
	@GetMapping
	public ResponseEntity<ApiResponse<List<GradeResponse>>> getAllGrades(
		@Parameter(description = "Número de página (inicia en 0)") @RequestParam(defaultValue = "0") int page,
		@Parameter(description = "Cantidad de elementos por página") @RequestParam(defaultValue = "100") int size,
		@Parameter(description = "Término de búsqueda para filtrar por nombre") @RequestParam(required = false) String search
	) {
		Pageable pageable = Pageable.ofSize(size).withPage(page);
		Page<GradeResponse> gradePage = gradeService.getAllGrade(pageable, search);
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<List<GradeResponse>>(
				HttpStatus.OK,
				"Grades retrieved successfully",
				gradePage.getContent(),
				PageInfo.fromPage(gradePage)
			)
		);
	}

	@Operation(
		summary = "Crear un nuevo grado",
		description = "Crea un nuevo grado escolar con la información proporcionada. " +
		              "El nombre del grado debe ser único."
	)
	@PostMapping
	public ResponseEntity<ApiResponse<GradeResponse>> createGrade(@Valid @RequestBody GradeRequest gradeRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
			new ApiResponse<GradeResponse>(
				HttpStatus.CREATED,
				"Grade created successfully",
				gradeService.createGrade(gradeRequest)
			)
		);
	}

	@Operation(
		summary = "Actualizar un grado",
		description = "Actualiza la información de un grado escolar existente por su ID. " +
		              "El grado debe existir en el sistema."
	)
	@PutMapping("/{gradeId}")
	public ResponseEntity<ApiResponse<GradeResponse>> updateGrade(
		@Parameter(description = "ID único del grado a actualizar") @PathVariable UUID gradeId,
		@Valid @RequestBody GradeRequest gradeRequest) {
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<GradeResponse>(
				HttpStatus.OK,
				"Grade updated successfully",
				gradeService.updateGrade(gradeId, gradeRequest)
			)
		);
	}

	@Operation(
		summary = "Eliminar un grado",
		description = "Elimina un grado escolar del sistema por su ID. " +
		              "El grado no debe tener configuraciones de grupo asociadas."
	)
	@DeleteMapping("/{gradeId}")
	public void deleteGrade(
		@Parameter(description = "ID único del grado a eliminar") @PathVariable UUID gradeId) {
		gradeService.deleteGrade(gradeId);
	}

}
