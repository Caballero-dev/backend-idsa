package com.api.idsa.security.service.impl;

import com.api.idsa.common.exception.RefreshTokenException;
import com.api.idsa.common.exception.ResourceNotFoundException;
import com.api.idsa.domain.personnel.model.UserEntity;
import com.api.idsa.domain.personnel.repository.IUserRepository;
import com.api.idsa.infrastructure.mail.service.MailService;
import com.api.idsa.security.dto.request.ForgotPasswordRequest;
import com.api.idsa.security.dto.request.LoginRequest;
import com.api.idsa.security.dto.request.PasswordSetRequest;
import com.api.idsa.security.dto.request.ResetPasswordRequest;
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

        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new RefreshTokenException("Refresh not found", "refresh_token_not_found");
        }

        try {
            String userEmail = jwtTokenProvider.extractUsername(refreshToken, false);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            if (jwtTokenProvider.isTokenValid(refreshToken, userDetails, false)) {
                String newAccessToken = jwtTokenProvider.generateToken(userDetails, true);
                ResponseCookie accessTokenCookie = cookieService.createAccessTokenCookie(newAccessToken);
                response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
            }
        } catch (ExpiredJwtException e) {
            throw new RefreshTokenException("Refresh token has expired", "expired_refresh_token");
        } catch (JwtException e) {
            throw new RefreshTokenException("Invalid refresh token", "invalid_refresh_token");
        }
    }

    @Override
    @Transactional
    public void verifyEmailAndSetPassword(PasswordSetRequest passwordSetRequest) {

        String email = emailTokenProvider.extractUsername(passwordSetRequest.getToken());
        String type = emailTokenProvider.extractType(passwordSetRequest.getToken());

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

        String email = emailTokenProvider.extractUsername(resetPasswordRequest.getToken());
        String type = emailTokenProvider.extractType(resetPasswordRequest.getToken());

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

        String newEmail = emailTokenProvider.extractUsername(token);
        String type = emailTokenProvider.extractType(token);

        if (!"EMAIL_CHANGE".equals(type)) {
            throw new RuntimeException("Invalid token type");
        }

        UserEntity userEntity = userRepository.findByEmail(emailTokenProvider.extractUsername(token))
                .orElseThrow(() -> new ResourceNotFoundException("update", "User", "email", newEmail));

        userEntity.setIsVerifiedEmail(true);
        userEntity.setIsActive(true);
        userRepository.save(userEntity);
    }

}
