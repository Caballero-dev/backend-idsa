package com.api.idsa.security.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.security.dto.request.ForgotPasswordRequest;
import com.api.idsa.security.dto.request.LoginRequest;
import com.api.idsa.security.dto.request.PasswordSetRequest;
import com.api.idsa.security.dto.request.RefreshTokenRequest;
import com.api.idsa.security.dto.request.ResetPasswordRequest;
import com.api.idsa.security.dto.response.TokenResponse;
import com.api.idsa.security.dto.request.ResendEmailRequest;
import com.api.idsa.security.service.IAuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(
    name = "Autenticación",
    description = "Endpoints para la gestión de autenticación y autorización de usuarios. " +
                  "Incluye login, refresh de tokens, verificación de email, restablecimiento de contraseña " +
                  "y reenvío de correos de verificación."
)
public class AuthController {

    @Autowired
    private IAuthService authService;

    @Operation(
        summary = "Iniciar sesión",
        description = "Autentica a un usuario con su email y contraseña. " +
                      "Retorna un access token y un refresh token. " +
                      "El usuario debe tener su email verificado y su cuenta activa."
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<TokenResponse>(
                HttpStatus.OK,
                "Login successful",
                authService.login(request)
            )
        );
    }

    @Operation(
        summary = "Refrescar token de acceso",
        description = "Genera un nuevo access token usando un refresh token válido. " +
                      "El access token solo se puede refrescar si está expirado o próximo a expirar. " +
                      "El refresh token debe estar vigente (no expirado)."
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<TokenResponse>(
                HttpStatus.OK,
                "Refresh token successful",
                authService.refreshToken(refreshTokenRequest)
            )
        );
    }

    @Operation(
        summary = "Verificar email y establecer contraseña",
        description = "Verifica el email del usuario mediante un token enviado por correo " +
                      "y establece su contraseña inicial. Este endpoint se usa después de que " +
                      "un administrador crea una cuenta de usuario."
    )
    @PostMapping("/verify-email")
    public void verifyEmail(@Valid @RequestBody PasswordSetRequest passwordSetRequest) {
        authService.verifyEmailAndSetPassword(passwordSetRequest);
    }

    @Operation(
        summary = "Solicitar restablecimiento de contraseña",
        description = "Envía un correo electrónico con un enlace para restablecer la contraseña. " +
                      "El token tiene un tiempo de expiración. Solo funciona para usuarios verificados y activos."
    )
    @PostMapping("/forgot-password")
    public void forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        authService.requestPasswordReset(forgotPasswordRequest);
    }

    @Operation(
        summary = "Restablecer contraseña",
        description = "Restablece la contraseña del usuario usando el token recibido por correo. " +
                      "El token debe ser válido y no estar expirado."
    )
    @PostMapping("/reset-password")
    public void resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        authService.resetPassword(resetPasswordRequest);
    }

    @Operation(
        summary = "Confirmar cambio de email",
        description = "Confirma el cambio de dirección de email del usuario mediante un token " +
                      "enviado a la nueva dirección de correo. El token tiene un tiempo de expiración."
    )
    @PostMapping("/confirm-email-change")
    public void confirmEmailChange(
        @Parameter(description = "Token de confirmación de cambio de email recibido por correo")
        @RequestParam String token) {
        authService.confirmEmailChange(token);
    }

    @Operation(
        summary = "Reenviar email de verificación",
        description = "Reenvía el email de verificación según el estado del usuario:\n\n" +
                      "- **Usuario no verificado sin contraseña**: Envía email de verificación inicial\n" +
                      "- **Usuario no verificado con contraseña**: Envía email de confirmación de cambio de email\n\n" +
                      "Este endpoint no retorna error si el usuario no existe por motivos de seguridad."
    )
    @PostMapping("/resend-email")
    public void resendEmail(@Valid @RequestBody ResendEmailRequest request) {
        authService.resendEmail(request);
    }

}
