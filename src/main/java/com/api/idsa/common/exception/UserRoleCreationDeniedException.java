package com.api.idsa.common.exception;

public class UserRoleCreationDeniedException extends RuntimeException {
    
    public UserRoleCreationDeniedException(String message) {
        super(message);
    }

    public UserRoleCreationDeniedException() {
        super("Creation of user with the role 'ADMIN' is denied");
    }

}
