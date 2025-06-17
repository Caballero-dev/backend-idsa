package com.api.idsa.common.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.api.idsa.common.response.ApiError;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.NOT_FOUND,
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(ResourceDependencyException.class)
    public ResponseEntity<ApiError> handleResourceDependencyException(ResourceDependencyException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.CONFLICT,
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiError> handleDuplicateResourceException(DuplicateResourceException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.CONFLICT,
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(UserRoleCreationDeniedException.class)
    public ResponseEntity<ApiError> handleUserRoleCreationDeniedException(UserRoleCreationDeniedException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.FORBIDDEN,
            ex.getMessage() + " <<role_denied>>",
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ApiError> handleIncorrectPasswordException(IncorrectPasswordException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.UNAUTHORIZED,
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<ApiError> handleEmailSendingException(EmailSendingException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ex.getMessage() + " <<email_sending_failed>>",
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {

        ApiError apiError;
        String path = request.getDescription(false).replace("uri=", "");

        if (ex.getCause() instanceof ConstraintViolationException constraintEx) {
            if (constraintEx.getErrorMessage().contains("foreign key constraint")) {
                apiError = new ApiError(
                    HttpStatus.CONFLICT,
                    "Failed: Operation cannot be completed due to data dependency <<foreign_key_violation>>",
                    path
                );
            } else if (constraintEx.getErrorMessage().contains("unique constraint")) {
                apiError = new ApiError(
                    HttpStatus.CONFLICT,
                    "Failed: Resource already exists <<resource_exists>>",
                    path
                );
            } else {
                apiError = new ApiError(
                    HttpStatus.CONFLICT,
                    "Failed: Data constraint violation <<constraint_violation>>",
                    path
                );
            }
        } else {
            apiError = new ApiError(
                HttpStatus.CONFLICT,
                "Failed: Data integrity violation <<data_integrity>>",
                path
            );
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.BAD_REQUEST,
            "Validation failed",
            request.getDescription(false).replace("uri=", "")
        );

        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            apiError.addValidationError(
                error.getField(),
                error.getCode(),
                error.getDefaultMessage()
            );
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(UnsatisfiedServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleUnsatisfiedServletRequestParameterException(UnsatisfiedServletRequestParameterException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.BAD_REQUEST,
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.BAD_REQUEST,
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.BAD_REQUEST,
            "The parameter '" + ex.getName() + "' should be of type " + ex.getRequiredType().getSimpleName() + " <<type_mismatch>>",
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.UNAUTHORIZED,
            ex.getMessage() + " <<invalid_credentials>>",
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex, WebRequest request) {

        ApiError apiError;
        String path = request.getDescription(false).replace("uri=", "");

        if (ex.getCause() instanceof LockedException) {
            apiError = new ApiError(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage() + " <<unverified_email>>",
                path
            );
        } else if (ex.getCause() instanceof DisabledException) {
            apiError = new ApiError(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage() + " <<account_inactive>>",
                path
            );
        } else if (ex.getCause() instanceof UnverifiedUserException) {
            apiError = new ApiError(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage(),
                path
            );
        } else {
            apiError = new ApiError(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage() + " <<authentication_failed>>",
                path
            );
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiError> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.UNAUTHORIZED,
            "Token has expired <<token_expired>>",
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJwtException(JwtException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.UNAUTHORIZED,
            "Invalid token <<invalid_token>>",
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(EmailTokenException.class)
    public ResponseEntity<ApiError> handleEmailTokenException(EmailTokenException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            ex.getHttpStatus(),
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(ex.getHttpStatus()).body(apiError);
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<ApiError> handleRefreshTokenException(RefreshTokenException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            ex.getHttpStatus(),
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(ex.getHttpStatus()).body(apiError);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        System.out.println();
        ApiError apiError = new ApiError(
            HttpStatus.METHOD_NOT_ALLOWED,
            ex.getMessage() + " <<method_not_allowed>>",
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(apiError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.BAD_REQUEST,
            "Malformed JSON request <<malformed_json>>",
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "An unexpected error occurred",
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }

}
