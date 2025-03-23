package com.api.idsa.exception;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String action, String entity, Long id) {
        super(String.format("Failed to %s: Entity %s with ID %d not found", action, entity, id));
    }

    public ResourceNotFoundException(String action, String entity, String field, String value) {
        super(String.format("Failed to %s: Entity %s with %s %s not found", action, entity, field, value));
    }

}
