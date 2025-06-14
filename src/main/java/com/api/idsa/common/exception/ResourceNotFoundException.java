package com.api.idsa.common.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String entity, String field, Long id) {
        super(String.format("Failed: %s with %s: %s <<%s_not_found>>", entity, field, id, entity.toLowerCase()));
    }

    public ResourceNotFoundException(String action, String entity, String field, String value) {
        super(String.format("Failed to %s: Entity %s with %s: %s not found", action, entity, field, value));
    }

    public ResourceNotFoundException(String entity, String field, String value) {
        super(String.format("Failed: %s with %s: %s <<%s_not_found>>", entity, field, value, entity.toLowerCase()));
    }

}
