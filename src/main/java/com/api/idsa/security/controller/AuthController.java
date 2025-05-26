package com.api.idsa.security.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.security.dto.request.ForgotPasswordRequest;
import com.api.idsa.security.dto.request.LoginRequest;
import com.api.idsa.security.dto.request.PasswordSetRequest;
import com.api.idsa.security.dto.request.RefreshTokenRequest;
import com.api.idsa.security.dto.request.ResetPasswordRequest;
import com.api.idsa.security.dto.response.TokenResponse;
import com.api.idsa.security.dto.request.ResendEmailByTokenRequest;
import com.api.idsa.security.service.IAuthService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

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

    @PostMapping("/verify-email")
    public void verifyEmail(@Valid @RequestBody PasswordSetRequest passwordSetRequest) {
        authService.verifyEmailAndSetPassword(passwordSetRequest);
    }

    @PostMapping("/forgot-password")
    public void forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        authService.requestPasswordReset(forgotPasswordRequest);
    }

    @PostMapping("/reset-password")
    public void resetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        authService.resetPassword(resetPasswordRequest);
    }

    @PostMapping("/confirm-email-change")
    public void confirmEmailChange(@RequestParam String token) {
        authService.confirmEmailChange(token);
    }

    @PostMapping("/resend-email")
    public void resendEmailByToken(@Valid @RequestBody ResendEmailByTokenRequest request) {
        authService.resendEmailByToken(request.getToken());
    }

}
