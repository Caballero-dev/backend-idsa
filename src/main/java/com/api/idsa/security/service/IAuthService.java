package com.api.idsa.security.service;

import com.api.idsa.security.dto.request.ForgotPasswordRequest;
import com.api.idsa.security.dto.request.LoginRequest;
import com.api.idsa.security.dto.request.PasswordSetRequest;
import com.api.idsa.security.dto.request.RefreshTokenRequest;
import com.api.idsa.security.dto.request.ResendEmailRequest;
import com.api.idsa.security.dto.request.ResetPasswordRequest;
import com.api.idsa.security.dto.response.TokenResponse;

public interface IAuthService {

    /**
     * Inicia sesión en la aplicación
     * 
     * @param loginRequest Objeto que contiene la información del usuario para iniciar sesión.
     */
    TokenResponse login(LoginRequest loginRequest);

    /**
     * Refresca el token de acceso.
     * 
     * @param refreshTokenRequest Objeto que contiene el token de acceso y el token de refresco.
     */
    TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    /**
     * Verifica el correo electrónico y establece la contraseña del usuario.
     * 
     * @param passwordSetRequest Objeto que contiene la información del usuario para verificar el correo electrónico y establecer la contraseña.
     */
    void verifyEmailAndSetPassword(PasswordSetRequest passwordSetRequest);

    /**
     * Solicita un restablecimiento de contraseña.
     * 
     * @param forgotPasswordRequest Objeto que contiene la información del usuario para solicitar un restablecimiento de contraseña.
     */
    void requestPasswordReset(ForgotPasswordRequest forgotPasswordRequest);

    /**
     * Restablece la contraseña del usuario.
     * 
     * @param resetPasswordRequest Objeto que contiene la información del usuario para restablecer la contraseña.
     */
    void resetPassword(ResetPasswordRequest resetPasswordRequest);

    /**
     * Confirma el cambio de correo electrónico.
     * 
     * @param token Token de confirmación de correo electrónico.
     */
    void confirmEmailChange(String token);

    /**
     * Reenvía un correo electrónico basado en el estado actual del usuario y el estado de verificación del correo.
     * 
     * Este método determina el correo electrónico apropiado para enviar según el estado de la cuenta del usuario:
     * 
     * 1. **Registro Inicial**: Si el usuario no está verificado, está inactivo y no tiene contraseña,
     *    se envía un correo de verificación para confirmar su dirección de correo electrónico.
     * 
     * 2. **Cambio de Correo Electrónico**: Si el usuario no está verificado, está inactivo, pero tiene una contraseña,
     *    se envía un correo de confirmación de cambio de correo electrónico.
     * 
     * // 3. **Restablecimiento de Contraseña**: Si el usuario está verificado, activo y tiene una contraseña,
     * //   se envía un correo de restablecimiento de contraseña.
     * 
     * Si el usuario no existe en el repositorio, no se realiza ninguna acción.
     * 
     * @param resendEmailRequest La solicitud que contiene la dirección de correo electrónico del usuario.
     */
    void resendEmail(ResendEmailRequest resendEmailRequest);

}
