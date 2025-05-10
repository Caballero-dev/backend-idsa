package com.api.idsa.infrastructure.mail.service;

public interface MailService {

    /**
     * Envia un correo de verificación de correo electrónico.
     * 
     * @param to    El correo electrónico del destinatario.
     * @param token El token de verificación.
     */
    void sendVerificationEmail(String to, String token);

    /**
     * Envia un correo de restablecimiento de contraseña.
     * 
     * @param to    El correo electrónico del destinatario.
     * @param token El token de restablecimiento de contraseña.
     */
    void sendPasswordResetEmail(String to, String token);

    /**
     * Envia un correo de confirmación de cambio de correo electrónico.
     * 
     * @param to    El correo electrónico del destinatario.
     * @param token El token de confirmación de cambio de correo electrónico.
     */
    void sendEmailChangeConfirmation(String to, String token);

}
