package com.api.idsa.domain.personnel.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.domain.personnel.dto.request.UpdatePasswordRequest;
import com.api.idsa.domain.personnel.dto.response.UserProfileResponse;
import com.api.idsa.domain.personnel.service.IUserProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
	name = "Perfil de Usuario",
	description = "Endpoints para la gestión del perfil del usuario autenticado. Permite consultar el perfil y actualizar la contraseña."
)
@RestController
@RequestMapping("/api/common/profile")
public class UserProfileController {

	@Autowired
	IUserProfileService userProfileService;

	@Operation(
		summary = "Obtener perfil del usuario autenticado",
		description = "Retorna la información del perfil del usuario actualmente autenticado en el sistema."
	)
	@GetMapping
	public ResponseEntity<ApiResponse<UserProfileResponse>> getUserProfile() {
		return ResponseEntity.status(HttpStatus.OK).body(
			new ApiResponse<UserProfileResponse>(
				HttpStatus.OK,
				"User profile retrieved successfully",
				userProfileService.getUserProfile()
			)
		);
	}

	@Operation(
		summary = "Actualizar contraseña del usuario",
		description = "Permite al usuario autenticado actualizar su contraseña proporcionando la contraseña actual y la nueva contraseña."
	)
	@PutMapping("/update-password")
	public void updatePassword(@Valid @RequestBody UpdatePasswordRequest request) {
		userProfileService.updatePassword(request);
	}
}