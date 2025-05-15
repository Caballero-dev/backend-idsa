package com.api.idsa.infrastructure.mail.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.api.idsa.infrastructure.mail.service.MailService;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Override
    public void sendVerificationEmail(String to, String token) {
        Context context = new Context();
        context.setVariable("verificationUrl", frontendUrl + "/verify-email?token=" + token);

        String emailContent = templateEngine.process("mail/verification-email", context);
        sendHtmlEmail(to, "Verify your email address", emailContent);
    }

    @Override
    public void sendPasswordResetEmail(String to, String token) {
        Context context = new Context();
        context.setVariable("resetUrl", frontendUrl + "/reset-password?token=" + token);

        String emailContent = templateEngine.process("mail/password-reset-email", context);
        sendHtmlEmail(to, "Reset your password", emailContent);
    }

    @Override
    public void sendEmailChangeConfirmation(String to, String token) {
        Context context = new Context();
        context.setVariable("confirmationUrl", frontendUrl + "/confirm-email-change?token=" + token);

        String emailContent = templateEngine.process("mail/email-change-confirmation", context);
        sendHtmlEmail(to, "Confirm your new email address", emailContent);
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
            throw new RuntimeException("Failed to send email", e);
        }
    }

}
