package com.api.idsa.common.exception;

import java.util.UUID;

public class ResourceDependencyException extends RuntimeException {

    public ResourceDependencyException(String message) {
        super(message);
    }
    
    public ResourceDependencyException(String entity, UUID id, String referencedBy, String dependencyReference) {
        super(String.format("Failed: %s with id: %s has %s <<%s_dependency>>", entity, id, referencedBy, dependencyReference));
    }

    public ResourceDependencyException(String entity, String field, String value, String referencedBy, String dependencyReference) {
        super(String.format("Failed: %s with %s: %s has %s <<%s_dependency>>", entity, field, value, referencedBy, dependencyReference));
    }
}
