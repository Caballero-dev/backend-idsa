package com.api.idsa.security.enums;

/**
 * Enum que representa los posibles estados de un token para refresh
 */
public enum TokenRefreshStatus {
    /**
     * Token válido y lejos de expirar - no necesita refresh
     */
    VALID,

    /**
     * Token válido pero cerca de expirar - necesita refresh
     */
    NEEDS_REFRESH,

    /**
     * Token expirado pero dentro del período de gracia - permite refresh
     */
    EXPIRED_REFRESHABLE,

    /**
     * Token expirado fuera del período de gracia - no permite refresh
     */
    EXPIRED_NON_REFRESHABLE,

    /**
     * Token inválido - no permite refresh
     */
    INVALID
}
