package com.api.idsa.common.exception;

public class RefreshTokenException extends RuntimeException {

    public RefreshTokenException(String message) {
        super(message);
    }

    public RefreshTokenException(String message, String errorCode) {
        super(String.format("%s <<%s>>", message, errorCode));
    }
    
}
