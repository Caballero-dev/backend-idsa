package com.api.idsa.security.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.api.idsa.security.enums.TokenType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Component
public class EmailTokenProvider {

    @Value("${jwt.verification.secret-key}")
    private String jwtVerificationSecretKey;

    @Value("${jwt.verification.expiration}")
    private int jwtVerificationExpirationInMinutes;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtVerificationSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateVerificationToken(String email, TokenType type) {
        return generateVerificationToken(new HashMap<>(), email, type);
    }

    public String generateVerificationToken(Map<String, Object> claims, String email, TokenType type) {
        return createToken(claims, email, type, jwtVerificationExpirationInMinutes);
    }

    private String createToken(Map<String, Object> claims, String email, TokenType type, int expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + TimeUnit.MINUTES.toMillis(expiration));

        return Jwts.builder()
                .header().type(type.toString()).and()
                .claims(claims)
                .subject(email)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public TokenType extractType(String token) {
        String typeStr = extractHeader(token, JwsHeader::getType);
        return TokenType.safeValueOf(typeStr);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractHeader(String token, Function<JwsHeader, T> headerResolver) {
        final JwsHeader header = extractAllHeader(token);
        return headerResolver.apply(header);
    }

    private JwsHeader extractAllHeader(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getHeader();
    }

}
