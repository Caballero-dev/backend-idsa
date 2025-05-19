package com.api.idsa.common.exception;

import org.springframework.http.HttpStatus;

public class EmailTokenException extends RuntimeException {
    private final HttpStatus httpStatus;

    public EmailTokenException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public EmailTokenException(String message, String errorCode, HttpStatus httpStatus) {
        super(String.format("%s <<%s>>", message, errorCode));
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
