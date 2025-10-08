package com.api.idsa.domain.personnel.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.personnel.dto.request.TutorRequest;
import com.api.idsa.domain.personnel.dto.response.TutorResponse;
import com.api.idsa.domain.personnel.service.ITutorService;

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

@Tag(
	name = "Tutores",
	description = "Endpoints para la gestión de tutores. Permite crear, consultar, actualizar y eliminar tutores del sistema."
)
@RestController
@RequestMapping("/api/admin/tutors")
public class TutorController {

	@Autowired
	ITutorService tutorService;

	@Operation(
		summary = "Obtener todos los tutores",
		description = "Retorna una lista paginada de todos los tutores registrados en el sistema. Permite búsqueda por nombre, apellidos, email o número de empleado."
	)
	@GetMapping
	public ResponseEntity<ApiResponse<List<TutorResponse>>> getAllTutor(
		@Parameter(description = "Número de página (inicia en 0)", example = "0")
		@RequestParam(defaultValue = "0") int page,
		@Parameter(description = "Cantidad de elementos por página", example = "100")
		@RequestParam(defaultValue = "100") int size,
		@Parameter(description = "Término de búsqueda (filtra por nombre, apellidos, email o número de empleado)")
		@RequestParam(required = false) String search
	) {
		Pageable pageable = Pageable.ofSize(size).withPage(page);
		Page<TutorResponse> tutorPage = tutorService.getAllTutor(pageable, search);
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<List<TutorResponse>>(
				HttpStatus.OK,
				"Tutors retrieved successfully",
				tutorPage.getContent(),
				PageInfo.fromPage(tutorPage)
			)
		);
	}

	@Operation(
		summary = "Crear un nuevo tutor",
		description = "Crea un nuevo tutor en el sistema. Se enviará un correo de verificación al email proporcionado."
	)
	@PostMapping
	public ResponseEntity<ApiResponse<TutorResponse>> createTutor(@Valid @RequestBody TutorRequest tutorRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
			new ApiResponse<TutorResponse>(
				HttpStatus.CREATED,
				"Tutor created successfully",
				tutorService.createTutor(tutorRequest)
			)
		);
	}

	@Operation(
		summary = "Actualizar un tutor",
		description = "Actualiza la información de un tutor existente en el sistema."
	)
	@PutMapping("/{tutorId}")
	public ResponseEntity<ApiResponse<TutorResponse>> updateTutor(
		@Parameter(description = "Identificador único del tutor", required = true)
		@PathVariable UUID tutorId,
		@Valid @RequestBody TutorRequest tutorRequest
	) {
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<TutorResponse>(
				HttpStatus.OK,
				"Tutor updated successfully",
				tutorService.updateTutor(tutorId, tutorRequest)
			)
		);
	}

	@Operation(
		summary = "Eliminar un tutor",
		description = "Elimina un tutor del sistema. No se puede eliminar si tiene grupos asignados."
	)
	@DeleteMapping("/{tutorId}")
	public void deleteTutor(
		@Parameter(description = "Identificador único del tutor", required = true)
		@PathVariable UUID tutorId
	) {
		tutorService.deleteTutor(tutorId);
	}

}
