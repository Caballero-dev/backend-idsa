package com.api.idsa.common.exception;

public class UnverifiedUserException extends RuntimeException {
    
    public UnverifiedUserException(String message) {
        super(message);
    }

    public UnverifiedUserException(String message, String errorCode) {
        super(String.format("%s <<%s>>", message, errorCode));
    }

}
