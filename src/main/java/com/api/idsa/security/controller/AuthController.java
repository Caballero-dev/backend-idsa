package com.api.idsa.security.controller;

import com.api.idsa.common.response.ApiResponse;
import com.api.idsa.security.dto.request.ForgotPasswordRequest;
import com.api.idsa.security.dto.request.LoginRequest;
import com.api.idsa.security.dto.request.PasswordSetRequest;
import com.api.idsa.security.dto.request.ResetPasswordRequest;
import com.api.idsa.security.dto.response.LoginResponse;
import com.api.idsa.security.service.IAuthService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(
            new ApiResponse<LoginResponse>(
                HttpStatus.OK,
                "Login successful",
                authService.login(request)
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

    @GetMapping("/confirm-email-change")
    public void confirmEmailChange(@RequestParam String token) {
        authService.confirmEmailChange(token);
    }

}
