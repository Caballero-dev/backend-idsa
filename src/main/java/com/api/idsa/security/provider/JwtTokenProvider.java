package com.api.idsa.security.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

    @Value("${jwt.access-token.secret-key}")
    private String jwtAccessSecretKey;

    @Value("${jwt.access-token.expiration}")
    private int jwtAccessExpirationInMinutes;

    @Value("${jwt.refresh-token.secret-key}")
    private String jwtRefreshSecretKey;

    @Value("${jwt.refresh-token.expiration}")
    private int jwtRefreshExpirationInMinutes;

    private SecretKey getAccessSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtAccessSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private SecretKey getRefreshSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtRefreshSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails, boolean isAccessToken) {
        return isAccessToken
                ? generateAccessToken(new HashMap<>(), userDetails)
                : generateRefreshToken(new HashMap<>(), userDetails);
    }

    public String generateAccessToken(Map<String, Object> claims, UserDetails userDetails) {
        return createToken(claims, userDetails, jwtAccessExpirationInMinutes, getAccessSigningKey(), "ACCESS_TOKEN");
    }

    public String generateRefreshToken(Map<String, Object> claims, UserDetails userDetails) {
        return createToken(claims, userDetails, jwtRefreshExpirationInMinutes, getRefreshSigningKey(), "REFRESH_TOKEN");
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails, int expiration, SecretKey signingKey, String type) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + TimeUnit.MINUTES.toMillis(expiration));

        return Jwts.builder()
                .header().type(type).and()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(signingKey)
                .compact();
    }

    public String extractUsername(String token, boolean isAccessToken) {
        return extractClaim(token, Claims::getSubject, isAccessToken);
    }

    // public String extractType(String token, boolean isAccessToken) {
    //     return extractHeader(token, JwsHeader::getType, isAccessToken);
    // }

    public boolean isTokenValid(String token, UserDetails userDetails, boolean isAccessToken) {
        final String username = extractUsername(token, isAccessToken);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token, isAccessToken);
    }

    private boolean isTokenExpired(String token, boolean isAccessToken) {
        return extractExpiration(token, isAccessToken).before(new Date());
    }

    private Date extractExpiration(String token, boolean isAccessToken) {
        return extractClaim(token, Claims::getExpiration, isAccessToken);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver, boolean isAccessToken) {
        final Claims claims = extractAllClaims(token, isAccessToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token, boolean isAccessToken) {
        SecretKey signingKey = isAccessToken ? getAccessSigningKey() : getRefreshSigningKey();
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // private <T> T extractHeader(String token, Function<JwsHeader, T> headerResolver, boolean isAccessToken) {
    //     final JwsHeader header = extractAllHeader(token, isAccessToken);
    //     return headerResolver.apply(header);
    // }

    // private JwsHeader extractAllHeader(String token, boolean isAccessToken) {
    //     SecretKey signingKey = isAccessToken ? getAccessSigningKey() : getRefreshSigningKey();
    //     return Jwts.parser()
    //             .verifyWith(signingKey)
    //             .build()
    //             .parseSignedClaims(token)
    //             .getHeader();
    // }

}
