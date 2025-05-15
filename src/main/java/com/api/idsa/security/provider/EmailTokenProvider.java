package com.api.idsa.security.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

@Component
public class EmailTokenProvider {

    @Value("${jwt.verification.secret}")
    private String verificationSecret;

    @Value("${jwt.verification.expiration}")
    private int verificationExpirationInMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(verificationSecret.getBytes());
    }

    public String generateVerificationToken(String email, String type) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + verificationExpirationInMs);

        Map<String, Object> claims = new HashMap<>();
        claims.put("type", type);

        return Jwts.builder()
                .subject(email)
                .claims(claims)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public String getTokenType(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get("type", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
