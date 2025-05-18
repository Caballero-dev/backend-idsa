package com.api.idsa.security.service.impl;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import com.api.idsa.security.service.ICookieService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CookieServiceImpl implements ICookieService {

    @Value("${jwt.cookie.domain}")
    private String cookieDomain;

    @Value("${jwt.cookie.access-token.name}")
    private String accessTokenName;

    @Value("${jwt.cookie.access-token.expiration}")
    private int accessTokenExpirationInMinutes;

    @Value("${jwt.cookie.refresh-token.name}")
    private String refreshTokenName;

    @Value("${jwt.cookie.refresh-token.expiration}")
    private int refreshTokenExpirationInMinutes;

    @Override
    public ResponseCookie createAccessTokenCookie(String token) {
        return createSecureCookie(accessTokenName, token, accessTokenExpirationInMinutes);
    }

    @Override
    public ResponseCookie createRefreshTokenCookie(String token) {
        return createSecureCookie(refreshTokenName, token, refreshTokenExpirationInMinutes);
    }

    @Override
    public ResponseCookie deleteAccessTokenCookie() {
        return deleteCookie(accessTokenName);
    }

    @Override
    public ResponseCookie deleteRefreshTokenCookie() {
        return deleteCookie(refreshTokenName);
    }

    @Override
    public String getAccessTokenFromCookie(HttpServletRequest request) {
        return getCookie(request, accessTokenName);
    }

    @Override
    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        return getCookie(request, refreshTokenName);
    }

    private ResponseCookie createSecureCookie(String name, String value, int maxAge) {
        return ResponseCookie.from(name, value)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(maxAge * 60)
                .domain(cookieDomain)
                .build();
    }

    private ResponseCookie deleteCookie(String name) {
        return ResponseCookie.from(name, "")
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/")
                .maxAge(0)
                .domain(cookieDomain)
                .build();
    }

    private String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        Optional<Cookie> jwtCookie = Arrays.stream(cookies)
                .filter(cookie -> name.equals(cookie.getName()))
                .findFirst();
        return jwtCookie.map(Cookie::getValue).orElse(null);
    }

}
