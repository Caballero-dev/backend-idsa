package com.api.idsa.security.service.impl;

import com.api.idsa.common.exception.EmailTokenException;
import com.api.idsa.common.exception.RefreshTokenException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.personnel.model.UserEntity;
import com.api.idsa.domain.personnel.repository.IUserRepository;
import com.api.idsa.infrastructure.mail.service.MailService;
import com.api.idsa.security.dto.request.ForgotPasswordRequest;
import com.api.idsa.security.dto.request.LoginRequest;
import com.api.idsa.security.dto.request.PasswordSetRequest;
import com.api.idsa.security.dto.request.RefreshTokenRequest;
import com.api.idsa.security.dto.request.ResendEmailRequest;
import com.api.idsa.security.dto.request.ResetPasswordRequest;
import com.api.idsa.security.dto.response.TokenResponse;
import com.api.idsa.security.enums.TokenType;
import com.api.idsa.security.enums.TokenRefreshStatus;
import com.api.idsa.security.provider.EmailTokenProvider;
import com.api.idsa.security.provider.JwtTokenProvider;
import com.api.idsa.security.service.IAuthService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtTokenProvider.generateToken(userDetails, true);
        String refreshToken = jwtTokenProvider.generateToken(userDetails, false);

        return TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        String accessToken = refreshTokenRequest.getAccessToken();

        try {

            TokenType accessTokenType = jwtTokenProvider.extractTokenTypeSafely(accessToken, true);
            TokenType refreshTokenType = jwtTokenProvider.extractTokenTypeSafely(refreshToken, false);

            if (accessTokenType != TokenType.ACCESS_TOKEN || refreshTokenType != TokenType.REFRESH_TOKEN) {
                throw new RefreshTokenException("Invalid token type", "invalid_token_type", HttpStatus.UNAUTHORIZED);
            }

            String userEmail = jwtTokenProvider.extractUsername(refreshToken, false);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            if (!jwtTokenProvider.isTokenValid(refreshToken, userDetails, false)) {
                throw new RefreshTokenException("Invalid refresh token", "invalid_refresh_token", HttpStatus.UNAUTHORIZED);
            }

            TokenRefreshStatus tokenStatus = jwtTokenProvider.checkAccessTokenRefreshStatus(accessToken);

            switch (tokenStatus) {
                case VALID:
                    throw new RefreshTokenException("Access token is still valid", "access_token_valid", HttpStatus.BAD_REQUEST);
                case NEEDS_REFRESH:
                case EXPIRED_REFRESHABLE:
                    String newAccessToken = jwtTokenProvider.generateToken(userDetails, true);
                    return TokenResponse.builder()
                            .accessToken(newAccessToken)
                            .refreshToken(refreshToken)
                            .build();
                case EXPIRED_NON_REFRESHABLE:
                    throw new RefreshTokenException("Access token expired and non-refreshable", "expired_non_refreshable_access_token", HttpStatus.UNAUTHORIZED);
                case INVALID:
                    throw new RefreshTokenException("Invalid access token", "invalid_access_token", HttpStatus.UNAUTHORIZED);
                default:
                    throw new RefreshTokenException("Invalid token", "invalid_token", HttpStatus.UNAUTHORIZED);
            }

        } catch (ExpiredJwtException e) {
            throw new RefreshTokenException("Refresh token has expired", "expired_refresh_token", HttpStatus.UNAUTHORIZED);
        } catch (JwtException e) {
            throw new RefreshTokenException("Invalid refresh token", "invalid_refresh_token", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @Transactional
    public void verifyEmailAndSetPassword(PasswordSetRequest passwordSetRequest) {
        try {
            String email = emailTokenProvider.extractUsername(passwordSetRequest.getToken());
            TokenType type = emailTokenProvider.extractType(passwordSetRequest.getToken());
            
            if (type == null || type != TokenType.EMAIL_VERIFICATION) {
                throw new EmailTokenException("Invalid token type", "invalid_verification_token", HttpStatus.BAD_REQUEST);
            }
            
            UserEntity userEntity = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));

            if (userEntity.getIsVerifiedEmail() || userEntity.getIsActive() || userEntity.getPassword() != null) {
                throw new EmailTokenException("Email has already been verified", "verified_email", HttpStatus.BAD_REQUEST);
            }

            userEntity.setPassword(passwordEncoder.encode(passwordSetRequest.getPassword()));
            userEntity.setIsActive(true);
            userEntity.setIsVerifiedEmail(true);
            userRepository.save(userEntity);
            
        } catch (ExpiredJwtException e) {
            throw new EmailTokenException("Email verification token has expired", "expired_verification_token", HttpStatus.UNAUTHORIZED);
        } catch (JwtException e) {
            throw new EmailTokenException("Invalid email verification token", "invalid_verification_token", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @Transactional
    public void requestPasswordReset(ForgotPasswordRequest forgotPasswordRequest) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(forgotPasswordRequest.getEmail()); 
        if (userOptional.isPresent() && userOptional.get().getIsActive() && userOptional.get().getIsVerifiedEmail()) {            
            String token = emailTokenProvider.generateVerificationToken(forgotPasswordRequest.getEmail(), TokenType.PASSWORD_RESET);
            mailService.sendPasswordResetEmail(forgotPasswordRequest.getEmail(), token);
        }
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {
        // FIXME: Verificar funcionamiento
        String email = emailTokenProvider.extractUsername(resetPasswordRequest.getToken());
        TokenType type = emailTokenProvider.extractType(resetPasswordRequest.getToken());

        if (type == null || type != TokenType.PASSWORD_RESET) {
            throw new EmailTokenException("Invalid token type", "invalid_token_type", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("reset password", "User", "email", email));

        if (!userEntity.getIsActive()) {
            throw new EmailTokenException("User account is not active", "account_inactive", HttpStatus.FORBIDDEN);
        }

        if (!userEntity.getIsVerifiedEmail()) {
            throw new EmailTokenException("Email has not been verified", "unverified_email", HttpStatus.BAD_REQUEST);
        }

        userEntity.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void confirmEmailChange(String token) {
        // FIXME: Verificar funcionamiento
        String newEmail = emailTokenProvider.extractUsername(token);
        TokenType type = emailTokenProvider.extractType(token);

        if (type == null || type != TokenType.EMAIL_CHANGE) {
            throw new EmailTokenException("Invalid token type", "invalid_token_type", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = userRepository.findByEmail(emailTokenProvider.extractUsername(token))
                .orElseThrow(() -> new ResourceNotFoundException("confirm email change", "User", "email", newEmail));
        
        if (userEntity.getIsVerifiedEmail()) {
            throw new EmailTokenException("Email has already been verified", "verified_email", HttpStatus.BAD_REQUEST);
        }

        userEntity.setIsVerifiedEmail(true);
        userEntity.setIsActive(true);
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void resendEmail(ResendEmailRequest resendEmailRequest) {
        String email = resendEmailRequest.getEmail();
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            
            UserEntity userEntity = userOptional.get();

            // Caso 1: Usuario no verificado, inactivo y sin contraseña (registro inicial)
            if (!userEntity.getIsVerifiedEmail() && !userEntity.getIsActive() && userEntity.getPassword() == null) {
                String verificationToken = emailTokenProvider.generateVerificationToken(email, TokenType.EMAIL_VERIFICATION);
                mailService.sendVerificationEmail(email, verificationToken);
                return;
            }

            // Caso 2: Usuario no verificado, inactivo y con contraseña (cambio de email)
            if (!userEntity.getIsVerifiedEmail() && !userEntity.getIsActive() && userEntity.getPassword() != null) {
                String verificationToken = emailTokenProvider.generateVerificationToken(email, TokenType.EMAIL_CHANGE);
                mailService.sendEmailChangeConfirmation(email, verificationToken);
                return;
            }

            // Caso 3: Usuario verificado, activo y con contraseña (reseteo de contraseña)
            // if (userEntity.getIsVerifiedEmail() && userEntity.getIsActive() && userEntity.getPassword() != null) {
            //     String resetToken = emailTokenProvider.generateVerificationToken(email, TokenType.PASSWORD_RESET);
            //     mailService.sendPasswordResetEmail(email, resetToken);
            //     return;
            // }
        }
    }

}
