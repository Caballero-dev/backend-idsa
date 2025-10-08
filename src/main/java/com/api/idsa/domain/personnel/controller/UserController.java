package com.api.idsa.domain.personnel.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.common.response.PageInfo;
import com.api.idsa.domain.personnel.dto.request.UserRequest;
import com.api.idsa.domain.personnel.dto.response.UserResponse;
import com.api.idsa.domain.personnel.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(
	name = "Usuarios",
	description = "Endpoints para la gestión de usuarios del sistema. Permite crear, consultar, actualizar y eliminar usuarios. Los usuarios pueden tener diferentes roles (tutores, administradores, etc.)."
)
@RestController
@RequestMapping("/api/admin/users")
public class UserController {

	@Autowired
	IUserService userService;

	@Operation(
		summary = "Obtener todos los usuarios",
		description = "Retorna una lista paginada de todos los usuarios del sistema (excepto ADMIN). Permite búsqueda por nombre, apellidos, email o número de empleado/matrícula."
	)
	@GetMapping
	public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers(
		@Parameter(description = "Número de página (inicia en 0)", example = "0")
		@RequestParam(defaultValue = "0") int page,
		@Parameter(description = "Cantidad de elementos por página", example = "100")
		@RequestParam(defaultValue = "100") int size,
		@Parameter(description = "Término de búsqueda (filtra por nombre, apellidos, email o número de empleado/matrícula)")
		@RequestParam(required = false) String search
	) {
		Pageable pageable = Pageable.ofSize(size).withPage(page);
		Page<UserResponse> userPage = userService.getAllUserExceptAdmin(pageable, search);
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<List<UserResponse>>(
				HttpStatus.OK,
				"Users retrieved successfully",
				userPage.getContent(),
				PageInfo.fromPage(userPage)
			)
		);
	}

	@Operation(
		summary = "Crear un nuevo usuario",
		description = "Crea un nuevo usuario en el sistema. Se enviará un correo de verificación al email proporcionado. La contraseña se establece mediante el proceso de verificación de email."
	)
	@PostMapping
	public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest userRequest) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
			new ApiResponse<UserResponse>(
				HttpStatus.CREATED,
				"User created successfully",
				userService.createUser(userRequest)
			)
		);
	}

	@Operation(
		summary = "Actualizar usuario con contraseña",
		description = "Actualiza la información de un usuario existente incluyendo su contraseña. El campo password es requerido cuando isUpdatePassword=true."
	)
	@PutMapping(value = "/{userId}", params = "isUpdatePassword=true")
	public ResponseEntity<ApiResponse<UserResponse>> updateUserWithPassword(
		@Parameter(description = "Identificador único del usuario", required = true)
		@PathVariable UUID userId,
		@Parameter(description = "Indica si se actualizará la contraseña (debe ser true en este endpoint)", required = true, example = "true")
		@RequestParam() boolean isUpdatePassword,
		@Validated(UserRequest.PasswordUpdate.class) @RequestBody() UserRequest updateUserRequest
	) {
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<UserResponse>(
				HttpStatus.OK,
				"User updated successfully",
				userService.updateUser(userId, isUpdatePassword, updateUserRequest)
			)
		);
	}

	@Operation(
		summary = "Actualizar usuario sin contraseña",
		description = "Actualiza la información de un usuario existente sin modificar su contraseña. El campo password no es requerido cuando isUpdatePassword=false."
	)
	@PutMapping(value = "/{userId}", params = "isUpdatePassword=false")
	public ResponseEntity<ApiResponse<UserResponse>> updateUserWithoutPassword(
		@Parameter(description = "Identificador único del usuario", required = true)
		@PathVariable UUID userId,
		@Parameter(description = "Indica si se actualizará la contraseña (debe ser false en este endpoint)", required = true, example = "false")
		@RequestParam() boolean isUpdatePassword,
		@Validated @RequestBody() UserRequest updateUserRequest
	) {
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<UserResponse>(
				HttpStatus.OK,
				"User updated successfully",
				userService.updateUser(userId, isUpdatePassword, updateUserRequest)
			)
		);
	}

	@Operation(
		summary = "Actualizar estado de usuario",
		description = "Activa o desactiva un usuario del sistema. Los usuarios desactivados no pueden acceder al sistema."
	)
	@PutMapping("/{userId}/status")
	public void updateUserStatus(
		@Parameter(description = "Identificador único del usuario", required = true)
		@PathVariable UUID userId,
		@Parameter(description = "Estado del usuario (true para activo, false para inactivo)", required = true, example = "true")
		@RequestParam boolean isActive
	) {
		userService.updateUserStatus(userId, isActive);
	}

	@Operation(
		summary = "Eliminar un usuario",
		description = "Elimina un usuario del sistema. Esta acción es irreversible."
	)
	@DeleteMapping("/{userId}")
	public void deleteUser(
		@Parameter(description = "Identificador único del usuario", required = true)
		@PathVariable UUID userId
	) {
		userService.deleteUser(userId);
	}

}
