package com.api.idsa.security.service;

import com.api.idsa.security.dto.request.ForgotPasswordRequest;
import com.api.idsa.security.dto.request.LoginRequest;
import com.api.idsa.security.dto.request.PasswordSetRequest;
import com.api.idsa.security.dto.request.ResetPasswordRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface IAuthService {

    /**
     * Inicia sesión en la aplicación
     * @param loginRequest Objeto que contiene la información del usuario para iniciar sesión.
     * @param response HttpServletResponse para manejar la respuesta HTTP.
     */
    void login(LoginRequest loginRequest, HttpServletResponse response);

    /** 
     * Cierra la sesión del usuario
     * @param response respuesta HTTP
     */
    void logout(HttpServletResponse response);

    /**
     * Refresca el token de acceso.
     * @param request HttpServletRequest para obtener el token de acceso.
     * @param response HttpServletResponse para manejar la respuesta HTTP.
     */
    void refreshToken(HttpServletRequest request, HttpServletResponse response);

    /**
     * Verifica el correo electrónico y establece la contraseña del usuario.
     * @param passwordSetRequest Objeto que contiene la información del usuario para verificar el correo electrónico y establecer la contraseña.
     */
    void verifyEmailAndSetPassword(PasswordSetRequest passwordSetRequest);

    /**
     * Solicita un restablecimiento de contraseña.
     * @param forgotPasswordRequest Objeto que contiene la información del usuario para solicitar un restablecimiento de contraseña.
     */
    void requestPasswordReset(ForgotPasswordRequest forgotPasswordRequest);

    /**
     * Restablece la contraseña del usuario.
     * @param resetPasswordRequest Objeto que contiene la información del usuario para restablecer la contraseña.
     */
    void resetPassword(ResetPasswordRequest resetPasswordRequest);

    /**
     * Confirma el cambio de correo electrónico.
     * @param token Token de confirmación de correo electrónico.
     */
    void confirmEmailChange(String token);

    /**
     * Reenvía un correo electrónico según el token proporcionado.
     * El tipo de email a enviar se determina automáticamente según el tipo del token.
     * 
     * @param token Token para determinar el tipo de correo y el destinatario
     */
    void resendEmailByToken(String token);
}
