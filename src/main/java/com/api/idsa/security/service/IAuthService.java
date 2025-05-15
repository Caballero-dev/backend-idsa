package com.api.idsa.security.service;

import com.api.idsa.security.dto.request.ForgotPasswordRequest;
import com.api.idsa.security.dto.request.LoginRequest;
import com.api.idsa.security.dto.request.PasswordSetRequest;
import com.api.idsa.security.dto.request.ResetPasswordRequest;
import com.api.idsa.security.dto.response.LoginResponse;

public interface IAuthService {

    /**
     * Inicia sesión en la aplicación
     * @param loginRequest Objeto que contiene la información del usuario para iniciar sesión.
     * @return {@link LoginResponse} con la información del usuario autenticado.
     */
    LoginResponse login(LoginRequest loginRequest);

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

}
