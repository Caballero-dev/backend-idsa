package com.api.idsa.exception;

public class UserRoleCreationDeniedException extends Exception {
    public UserRoleCreationDeniedException(String message) {
        super(message);
    }

    public UserRoleCreationDeniedException() {
        super("Creation of user with the role 'ADMIN' is denied");
    }

}
