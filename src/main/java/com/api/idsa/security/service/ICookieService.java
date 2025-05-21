package com.api.idsa.security.service;

import org.springframework.http.ResponseCookie;

import jakarta.servlet.http.HttpServletRequest;

public interface ICookieService {

    /**
     * Crea un cookie con el token de acceso
     * 
     * @param token token de acceso
     * @return cookie con el token de acceso
     */
    ResponseCookie createAccessTokenCookie(String token);

    /**
     * Crea un cookie con el token de actualización
     * 
     * @param token token de actualización
     * @return cookie con el token de actualización
     */
    ResponseCookie createRefreshTokenCookie(String token);

    /**
     * Elimina el cookie de acceso
     * 
     * @return cookie eliminada
     */
    ResponseCookie deleteAccessTokenCookie();

    /**
     * Elimina el cookie de actualización
     * 
     * @return cookie eliminada
     */
    ResponseCookie deleteRefreshTokenCookie();

    /**
     * Obtiene el token de acceso del cookie
     * 
     * @param request petición HTTP
     * @return token de acceso
     */
    String getAccessTokenFromCookie(HttpServletRequest request);

    /**
     * Obtiene el token de actualización del cookie
     * 
     * @param request petición HTTP
     * @return token de actualización
     */
    String getRefreshTokenFromCookie(HttpServletRequest request);

}
