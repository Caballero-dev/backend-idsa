package com.api.idsa.security.controller;

import com.api.idsa.security.dto.request.ForgotPasswordRequest;
import com.api.idsa.security.dto.request.LoginRequest;
import com.api.idsa.security.dto.request.PasswordSetRequest;
import com.api.idsa.security.dto.request.ResetPasswordRequest;
import com.api.idsa.security.service.IAuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    // TODO: agregar controlador de volver a pedir correo de verificacion, etc.

    @Autowired
    private IAuthService authService;

    @PostMapping("/login")
    public void login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        authService.login(request, response);
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        authService.logout(response);
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        authService.refreshToken(request, response);
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

}
