package com.api.idsa.exception;

public class DuplicateResourceException extends Exception {

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String action, String entity, String name) {
        super(String.format("Failed to %s: Entity %s with name %s already exists", action, entity, name));
    }

    public DuplicateResourceException(String action, String entity, String field, String value) {
        super(String.format("Failed to %s: Entity %s with %s %s already exists", action, entity, field, value));
    }

}
