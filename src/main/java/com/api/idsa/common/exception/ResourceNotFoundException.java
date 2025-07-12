package com.api.idsa.common.exception;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String entity, String field, UUID value) {
        super(String.format("Failed: %s with %s: %s not found <<%s_not_found>>", entity, field, value, entity.toLowerCase().replace(" ", "_")));
    }

    public ResourceNotFoundException(String entity, String field, String value) {
        super(String.format("Failed: %s with %s: %s not found <<%s_not_found>>", entity, field, value, entity.toLowerCase().replace(" ", "_")));
    }

}
