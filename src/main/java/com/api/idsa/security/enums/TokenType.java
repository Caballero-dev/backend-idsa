package com.api.idsa.security.enums;

/**
 * Enum que representa diferentes tipos de tokens de correo electrónico.
 */
public enum TokenType {
    /**
     * Tipo de token para la verificación de correo electrónico.
     */
    EMAIL_VERIFICATION,
    /**
     * Tipo de token para restablecimiento de contraseña.
     */
    PASSWORD_RESET,
    /**
     * Tipo de token para la confirmación de cambio de correo electrónico.
     */
    EMAIL_CHANGE,
    /**
     * Tipo de token para la verificación de correo electrónico de inicio de sesión.
     */
    ACCESS_TOKEN,
    /**
     * Tipo de token para la verificación de correo electrónico de actualización.
     */
    REFRESH_TOKEN;

    /**
     * Convierte de forma segura una cadena en un valor de enumeración TokenType.
     *
     * @param name el string a convertir
     * @return el TokenType correspondiente o null si no es válido/nulo
     */
    public static TokenType safeValueOf(String name) {
        try {
            if (name == null) return null;
            return TokenType.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
