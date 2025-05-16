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
     * Elimina el cookie de acceso
     * 
     * @return cookie eliminada
     */
    ResponseCookie deleteAccessTokenCookie();

    /**
     * Obtiene el token de acceso del cookie
     * 
     * @param request petición HTTP
     * @return token de acceso
     */
    String getAccessTokenFromCookie(HttpServletRequest request);

}
