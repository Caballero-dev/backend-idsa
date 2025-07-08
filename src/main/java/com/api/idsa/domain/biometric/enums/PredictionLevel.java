package com.api.idsa.domain.biometric.enums;

/**
 * Enum que representa los niveles de predicción de un reporte.
 */
public enum PredictionLevel {
    /**
     * Nivel de predicción bajo.
     */
    BAJA,
    /**
     * Nivel de predicción medio.
     */
    MEDIA,
    /**
     * Nivel de predicción alto.
     */
    ALTA;

    /**
     * Convierte de forma segura una cadena en un valor de enumeración PredictionLevel.
     *
     * @param name el string a convertir
     * @return el PredictionLevel correspondiente o null si no es válido/nulo
     */
    public static PredictionLevel safeValueOf(String name) {
        try {
            if (name == null) return null;
            return PredictionLevel.valueOf(name);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
}