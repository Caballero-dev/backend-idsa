package com.api.idsa.common.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
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
            ex.getMessage(),
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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.CONFLICT,
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
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
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.UNAUTHORIZED,
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
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

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<ApiError> handleRefreshTokenException(RefreshTokenException ex, WebRequest request) {
        ApiError apiError = new ApiError(
            HttpStatus.UNAUTHORIZED,
            ex.getMessage(),
            request.getDescription(false).replace("uri=", "")
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
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
