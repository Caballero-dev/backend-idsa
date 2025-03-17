package com.api.idsa.exception;

public class DuplicateResourceException extends Exception {

    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String action, String entity, String name) {
        super(String.format("Failed to %s: Entity %s with name %s already exists", action, entity, name));
    }

}
