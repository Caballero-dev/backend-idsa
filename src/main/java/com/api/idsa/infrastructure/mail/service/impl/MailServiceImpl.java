package com.api.idsa.infrastructure.mail.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.api.idsa.common.exception.EmailSendingException;
import com.api.idsa.infrastructure.mail.service.MailService;
import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.model.message.Message;
import com.mailgun.model.message.MessageResponse;

import feign.FeignException;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private MailgunMessagesApi mailgunMessagesApi;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${jwt.verification.expiration}")
    private int verificationExpirationInMinutes;

    @Value("${mailgun.domain}")
    private String domain;

    @Value("${mailgun.from.email}")
    private String emailFrom;

    @Override
    public void sendVerificationEmail(String to, String token) {
        Context context = new Context();
        context.setVariable("verificationUrl", frontendUrl + "/auth/verify-email?token=" + token);
        context.setVariable("verificationExpiration", verificationExpirationInMinutes);

        String emailContent = templateEngine.process("mail/verification-email", context);
        sendHtmlEmail(to, "Verifica tu dirección de correo electrónico", emailContent);
    }

    @Override
    public void sendPasswordResetEmail(String to, String token) {
        Context context = new Context();
        context.setVariable("resetUrl", frontendUrl + "/auth/reset-password?token=" + token);
        context.setVariable("resetExpiration", verificationExpirationInMinutes);

        String emailContent = templateEngine.process("mail/password-reset-email", context);
        sendHtmlEmail(to, "Restablece tu contraseña", emailContent);
    }

    @Override
    public void sendEmailChangeConfirmation(String to, String token) {
        Context context = new Context();
        context.setVariable("confirmationUrl", frontendUrl + "/auth/confirm-email-change?token=" + token);
        context.setVariable("confirmationExpiration", verificationExpirationInMinutes);

        String emailContent = templateEngine.process("mail/email-change-confirmation", context);
        sendHtmlEmail(to, "Confirma tu nuevo correo electrónico", emailContent);
    }

    private void sendHtmlEmail(String to, String subject, String htmlContent) {
        log.info("Intentando enviar correo a: {}", to);
        try {

            Message message = Message.builder()
                .from(emailFrom)
                .to(to)
                .subject(subject)
                .html(htmlContent)
                .build();

                MessageResponse response = mailgunMessagesApi.sendMessage(domain, message);
                log.info("Email enviado correctamente a {}: {}", to, response.getId());
            } catch (FeignException e) {
                log.error("Error de Mailgun al enviar email a {}: Status: {}, Mensaje: {}", 
                    to, e.status(), e.getMessage());
                throw new EmailSendingException("Error al enviar email a través de Mailgun");
            } catch (Exception e) {
                log.error("Error inesperado al enviar email a {}: {}", to, e.getMessage());
                throw new EmailSendingException("Error inesperado al enviar email");
            }
    }

}
