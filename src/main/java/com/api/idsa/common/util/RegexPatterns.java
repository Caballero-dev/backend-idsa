package com.api.idsa.common.util;

public class RegexPatterns {

    public static final String ONLY_LETTERS = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*$";
    public static final String ONLY_PLAIN_LETTERS = "^[a-zA-Z]+$";
    public static final String PASSWORD = "^[a-zA-Z0-9ñÑ!@#$%^&*()_+\\-=\\[\\]{}|;:'\"\\\\,.<>\\/\\?~`]+$";
    public static final String ONLY_NUMBERS = "^[0-9]+$";
    public static final String ALPHANUMERIC = "^[a-zA-Z0-9]+$";

}
