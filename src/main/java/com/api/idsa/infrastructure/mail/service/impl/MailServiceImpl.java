package com.api.idsa.infrastructure.mail.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.api.idsa.common.exception.EmailSendingException;
import com.api.idsa.infrastructure.mail.service.MailService;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Value("${jwt.verification.expiration}")
    private int verificationExpirationInMinutes;

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
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new EmailSendingException("Error configuring email message");
        } catch (MailException e) {
            throw new EmailSendingException("Failed to send email");
        } catch (Exception e) {
            throw new EmailSendingException("An unexpected error occurred while sending email");
        }
    }

}
