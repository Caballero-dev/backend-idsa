package com.api.idsa.domain.personnel.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.personnel.dto.request.StudentRequest;
import com.api.idsa.domain.personnel.dto.response.StudentResponse;
import com.api.idsa.domain.personnel.service.IStudentService;

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
	name = "Estudiantes",
	description = "Endpoints para la gestión de estudiantes. Permite crear, consultar, actualizar y eliminar estudiantes del sistema."
)
@RestController
@RequestMapping("/api/common/students")
public class StudentController {

	@Autowired
	IStudentService studentService;

	@Operation(
		summary = "Obtener todos los estudiantes",
		description = "Retorna una lista paginada de todos los estudiantes registrados en el sistema. Permite búsqueda por nombre, apellidos o matrícula."
	)
	@GetMapping
	public ResponseEntity<ApiResponse<List<StudentResponse>>> getAllStudents(
		@Parameter(description = "Número de página (inicia en 0)", example = "0")
		@RequestParam(defaultValue = "0") int page,
		@Parameter(description = "Cantidad de elementos por página", example = "100")
		@RequestParam(defaultValue = "100") int size,
		@Parameter(description = "Término de búsqueda (filtra por nombre, apellidos o matrícula)")
		@RequestParam(required = false) String search
	) {
		Pageable pageable = Pageable.ofSize(size).withPage(page);
		Page<StudentResponse> studentPage = studentService.getAllStudent(pageable, search);
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<List<StudentResponse>>(
				HttpStatus.OK,
				"Students retrieved successfully",
				studentPage.getContent(),
				PageInfo.fromPage(studentPage)
			)
		);
	}

	@Operation(
		summary = "Obtener estudiantes por configuración de grupo",
		description = "Retorna una lista paginada de estudiantes que pertenecen a una configuración de grupo específica. Permite búsqueda por nombre, apellidos o matrícula."
	)
	@GetMapping("/by-group/{groupConfigurationId}")
	public ResponseEntity<ApiResponse<List<StudentResponse>>> getStudentsByGroupConfigurationId(
		@Parameter(description = "Identificador único de la configuración de grupo", required = true)
		@PathVariable UUID groupConfigurationId,
		@Parameter(description = "Número de página (inicia en 0)", example = "0")
		@RequestParam(defaultValue = "0") int page,
		@Parameter(description = "Cantidad de elementos por página", example = "100")
		@RequestParam(defaultValue = "100") int size,
		@Parameter(description = "Término de búsqueda (filtra por nombre, apellidos o matrícula)")
		@RequestParam(required = false) String search
	) {
		Pageable pageable = Pageable.ofSize(size).withPage(page);
		Page<StudentResponse> studentPage = studentService.getStudentsByGroupConfigurationId(groupConfigurationId, search, pageable);
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<List<StudentResponse>>(
				HttpStatus.OK,
				"Students for group configuration ID " + groupConfigurationId + " retrieved successfully",
				studentPage.getContent(),
				PageInfo.fromPage(studentPage)
			)
		);
	}

	@Operation(
		summary = "Crear un nuevo estudiante",
		description = "Crea un nuevo estudiante en el sistema y lo asigna a una configuración de grupo específica."
	)
	@PostMapping("/by-group/{groupConfigurationId}")
	public ResponseEntity<ApiResponse<StudentResponse>> createStudent(
		@Valid @RequestBody StudentRequest studentRequest,
		@Parameter(description = "Identificador único de la configuración de grupo", required = true)
		@PathVariable UUID groupConfigurationId
	) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
			new ApiResponse<StudentResponse>(
				HttpStatus.CREATED,
				"Student created successfully",
				studentService.createStudent(studentRequest, groupConfigurationId)
			)
		);
	}

	@Operation(
		summary = "Actualizar un estudiante",
		description = "Actualiza la información de un estudiante existente en el sistema."
	)
	@PutMapping("/{studentId}")
	public ResponseEntity<ApiResponse<StudentResponse>> updateStudent(
		@Parameter(description = "Identificador único del estudiante", required = true)
		@PathVariable UUID studentId,
		@Valid @RequestBody StudentRequest studentRequest
	) {
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<StudentResponse>(
				HttpStatus.OK,
				"Student updated successfully",
				studentService.updateStudent(studentId, studentRequest)
			)
		);
	}

	@Operation(
		summary = "Eliminar un estudiante",
		description = "Elimina un estudiante del sistema."
	)
	@DeleteMapping("/{studentId}")
	public void deleteStudent(
		@Parameter(description = "Identificador único del estudiante", required = true)
		@PathVariable UUID studentId
	) {
		studentService.deleteStudent(studentId);
	}

}
