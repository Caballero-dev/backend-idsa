package com.api.idsa.security.service.impl;

import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.personnel.model.UserEntity;
import com.api.idsa.domain.personnel.repository.IUserRepository;
import com.api.idsa.infrastructure.mail.service.MailService;
import com.api.idsa.security.dto.request.ForgotPasswordRequest;
import com.api.idsa.security.dto.request.LoginRequest;
import com.api.idsa.security.dto.request.PasswordSetRequest;
import com.api.idsa.security.dto.request.ResetPasswordRequest;
import com.api.idsa.security.dto.response.LoginResponse;
import com.api.idsa.security.provider.EmailTokenProvider;
import com.api.idsa.security.provider.JwtTokenProvider;
import com.api.idsa.security.service.IAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private EmailTokenProvider emailTokenProvider;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        return LoginResponse.builder()
                .token(jwtTokenProvider.generateToken(authentication))
                .build();
    }

    @Override
    @Transactional
    public void verifyEmailAndSetPassword(PasswordSetRequest passwordSetRequest) {
        if (!emailTokenProvider.validateToken(passwordSetRequest.getToken())) {
            throw new RuntimeException("Invalid or expired token");
        }

        String email = emailTokenProvider.getEmailFromToken(passwordSetRequest.getToken());
        String type = emailTokenProvider.getTokenType(passwordSetRequest.getToken());

        if (!"EMAIL_VERIFICATION".equals(type)) {
            throw new RuntimeException("Invalid token type");
        }

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("update", "User", "email", email));

        userEntity.setPassword(passwordEncoder.encode(passwordSetRequest.getPassword()));
        userEntity.setIsActive(true);
        userEntity.setIsVerifiedEmail(true);
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void requestPasswordReset(ForgotPasswordRequest forgotPasswordRequest) {
        if (!userRepository.existsByEmail(forgotPasswordRequest.getEmail())) {
            throw new ResourceNotFoundException("get", "User", "email", forgotPasswordRequest.getEmail());
        }

        String token = emailTokenProvider.generateVerificationToken(forgotPasswordRequest.getEmail(), "PASSWORD_RESET");
        mailService.sendPasswordResetEmail(forgotPasswordRequest.getEmail(), token);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        if (!emailTokenProvider.validateToken(resetPasswordRequest.getToken())) {
            throw new RuntimeException("Invalid or expired token");
        }

        String email = emailTokenProvider.getEmailFromToken(resetPasswordRequest.getToken());
        String type = emailTokenProvider.getTokenType(resetPasswordRequest.getToken());

        if (!"PASSWORD_RESET".equals(type)) {
            throw new RuntimeException("Invalid token type");
        }

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("update", "User", "email", email));

        userEntity.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void confirmEmailChange(String token) {
        if (!emailTokenProvider.validateToken(token)) {
            throw new RuntimeException("Invalid or expired token");
        }

        String newEmail = emailTokenProvider.getEmailFromToken(token);
        String type = emailTokenProvider.getTokenType(token);

        if (!"EMAIL_CHANGE".equals(type)) {
            throw new RuntimeException("Invalid token type");
        }

        UserEntity userEntity = userRepository.findByEmail(emailTokenProvider.getEmailFromToken(token))
                .orElseThrow(() -> new ResourceNotFoundException("update", "User", "email", newEmail));

        userEntity.setIsVerifiedEmail(true);
        userEntity.setIsActive(true);
        userRepository.save(userEntity);
    }

}
