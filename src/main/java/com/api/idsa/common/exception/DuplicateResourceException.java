package com.api.idsa.common.exception;

public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String entity, String field, String value) {
        super(String.format("Failed: %s with %s: %s already exists <<%s_already_exists>>", entity, field, value, field.toLowerCase()));
    }

}
