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
import com.api.idsa.security.dto.request.ResetPasswordRequest;
import com.api.idsa.security.enums.TokenType;
import com.api.idsa.security.enums.TokenRefreshStatus;
import com.api.idsa.security.provider.EmailTokenProvider;
import com.api.idsa.security.provider.JwtTokenProvider;
import com.api.idsa.security.service.IAuthService;
import com.api.idsa.security.service.ICookieService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
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
    private ICookieService cookieService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void login(LoginRequest loginRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
            )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String accessToken = jwtTokenProvider.generateToken(userDetails, true);
        String refreshToken = jwtTokenProvider.generateToken(userDetails, false);

        ResponseCookie accessTokenCookie = cookieService.createAccessTokenCookie(accessToken);
        ResponseCookie refreshTokenCookie = cookieService.createRefreshTokenCookie(refreshToken);

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }

    @Override
    public void logout(HttpServletResponse response) {
        ResponseCookie accessTokenCookie = cookieService.deleteAccessTokenCookie();
        ResponseCookie refreshTokenCookie = cookieService.deleteRefreshTokenCookie();
        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = cookieService.getRefreshTokenFromCookie(request);
        String accessToken = cookieService.getAccessTokenFromCookie(request);

        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new RefreshTokenException("Refresh token not found", "refresh_token_not_found", HttpStatus.UNAUTHORIZED);
        }
        if (accessToken == null || accessToken.isEmpty()) {
            throw new RefreshTokenException("Access token not found", "access_token_not_found", HttpStatus.UNAUTHORIZED);
        }

        try {

            TokenType accessTokenType = jwtTokenProvider.extractTokenType(accessToken, true);
            TokenType refreshTokenType = jwtTokenProvider.extractTokenType(refreshToken, false);

            if (accessTokenType != TokenType.ACCESS_TOKEN || refreshTokenType != TokenType.REFRESH_TOKEN) {
                clearAuthCookies(response);
                throw new RefreshTokenException("Invalid token type", "invalid_token_type", HttpStatus.UNAUTHORIZED);
            }

            String userEmail = jwtTokenProvider.extractUsername(refreshToken, false);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            if (!jwtTokenProvider.isTokenValid(refreshToken, userDetails, false)) {
                clearAuthCookies(response);
                throw new RefreshTokenException("Invalid refresh token", "invalid_refresh_token", HttpStatus.UNAUTHORIZED);
            }

            TokenRefreshStatus tokenStatus = jwtTokenProvider.checkAccessTokenRefreshStatus(accessToken);

            switch (tokenStatus) {
                case VALID:
                    throw new RefreshTokenException("Access token is still valid", "access_token_valid", HttpStatus.BAD_REQUEST);
                case NEEDS_REFRESH:
                case EXPIRED_REFRESHABLE:
                    String newAccessToken = jwtTokenProvider.generateToken(userDetails, true);
                    ResponseCookie newAccessTokenCookie = cookieService.createAccessTokenCookie(newAccessToken);
                    response.addHeader(HttpHeaders.SET_COOKIE, newAccessTokenCookie.toString());
                    break;
                case EXPIRED_NON_REFRESHABLE:
                    clearAuthCookies(response);
                    throw new RefreshTokenException("Access token expired and non-refreshable", "expired_non_refreshable_access_token", HttpStatus.UNAUTHORIZED);
                case INVALID:
                    clearAuthCookies(response);
                    throw new RefreshTokenException("Invalid access token", "invalid_access_token", HttpStatus.UNAUTHORIZED);
            }

        } catch (ExpiredJwtException e) {
            clearAuthCookies(response);
            throw new RefreshTokenException("Refresh token has expired", "expired_refresh_token", HttpStatus.UNAUTHORIZED);
        } catch (JwtException e) {
            clearAuthCookies(response);
            throw new RefreshTokenException("Invalid refresh token", "invalid_refresh_token", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @Transactional
    public void verifyEmailAndSetPassword(PasswordSetRequest passwordSetRequest) {

        String email = emailTokenProvider.extractUsername(passwordSetRequest.getToken());
        TokenType type = emailTokenProvider.extractType(passwordSetRequest.getToken());

        if (type == null || type != TokenType.EMAIL_VERIFICATION) {
            throw new EmailTokenException("Invalid token type", "invalid_token_type", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("verify email", "User", "email", email));

        if (userEntity.getIsVerifiedEmail()) {
            throw new EmailTokenException("Email has already been verified", "verified_email", HttpStatus.BAD_REQUEST);
        }

        userEntity.setPassword(passwordEncoder.encode(passwordSetRequest.getPassword()));
        userEntity.setIsActive(true);
        userEntity.setIsVerifiedEmail(true);
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void requestPasswordReset(ForgotPasswordRequest forgotPasswordRequest) {
        UserEntity userEntity = userRepository.findByEmail(forgotPasswordRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("process password reset", "User", "email", forgotPasswordRequest.getEmail()));

        if (!userEntity.getIsActive()) {
            throw new EmailTokenException("User account is not active", "account_inactive", HttpStatus.FORBIDDEN);
        }

        String token = emailTokenProvider.generateVerificationToken(forgotPasswordRequest.getEmail(), TokenType.PASSWORD_RESET);
        mailService.sendPasswordResetEmail(forgotPasswordRequest.getEmail(), token);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {

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
    public void resendEmailByToken(String token) {
        try {
            String email = emailTokenProvider.extractUsername(token);
            TokenType tokenType = emailTokenProvider.extractType(token);

            processEmailResend(email, tokenType);

        } catch (ExpiredJwtException e) {
            String email = e.getClaims().getSubject();
            TokenType tokenType = TokenType.safeValueOf(e.getHeader().getType());

            processEmailResend(email, tokenType);
        } catch (JwtException e) {
            throw new EmailTokenException("Invalid token", "invalid_token", HttpStatus.UNAUTHORIZED);
        }
    }

    private void processEmailResend(String email, TokenType tokenType) {

        if (tokenType == null) {
            throw new EmailTokenException("Invalid token type", "invalid_token_type", HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("resend email", "User", "email", email));
        
        switch (tokenType) {
            case EMAIL_VERIFICATION:
                if (userEntity.getIsVerifiedEmail()) {
                    throw new EmailTokenException("Email has already been verified", "verified_email", HttpStatus.BAD_REQUEST);
                }
                String verificationToken = emailTokenProvider.generateVerificationToken(email, tokenType);
                mailService.sendVerificationEmail(email, verificationToken);
                break;
                
            case PASSWORD_RESET:
                if (!userEntity.getIsActive()) {
                    throw new EmailTokenException("User account is not active", "account_inactive", HttpStatus.FORBIDDEN);
                }
                String resetToken = emailTokenProvider.generateVerificationToken(email, tokenType);
                mailService.sendPasswordResetEmail(email, resetToken);
                break;
                
            case EMAIL_CHANGE:
                if (userEntity.getIsVerifiedEmail()) {
                    throw new EmailTokenException("Email has already been verified", "verified_email", HttpStatus.BAD_REQUEST);
                }
                String emailChangeToken = emailTokenProvider.generateVerificationToken(email, tokenType);
                mailService.sendEmailChangeConfirmation(email, emailChangeToken);
                break;
                
            default:
                throw new EmailTokenException("Unknown email type", "unknown_email_type", HttpStatus.BAD_REQUEST);
        }
    }
    
    private void clearAuthCookies(HttpServletResponse response) {
        ResponseCookie accessTokenCookie = cookieService.deleteAccessTokenCookie();
        ResponseCookie refreshTokenCookie = cookieService.deleteRefreshTokenCookie();
        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }

}
