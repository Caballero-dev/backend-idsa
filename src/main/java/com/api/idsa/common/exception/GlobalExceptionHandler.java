package com.api.idsa.common.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.api.idsa.common.response.ErrorMessageResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessageResponse errorMessageResponse = ErrorMessageResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND.getReasonPhrase())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .error("Resource Not Found")
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessageResponse);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorMessageResponse> handleDuplicateResourceException(DuplicateResourceException ex, WebRequest request) {
        ErrorMessageResponse errorMessageResponse = ErrorMessageResponse.builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .status(HttpStatus.CONFLICT.getReasonPhrase())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .error("Duplicate Resource")
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessageResponse);
    }

    @ExceptionHandler(UserRoleCreationDeniedException.class)
    public ResponseEntity<ErrorMessageResponse> handleUserRoleCreationDeniedException(UserRoleCreationDeniedException ex, WebRequest request) {
        ErrorMessageResponse errorMessageResponse = ErrorMessageResponse.builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .status(HttpStatus.FORBIDDEN.getReasonPhrase())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .error("User Creation Denied")
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessageResponse);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorMessageResponse> handleIncorrectPasswordException(IncorrectPasswordException ex, WebRequest request) {
        ErrorMessageResponse errorMessageResponse = ErrorMessageResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .error("Incorrect Password")
                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessageResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessageResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        ErrorMessageResponse errorMessageResponse = ErrorMessageResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .message(ex.getMessage())
                .path(request.getDescription(false).replace("uri=", ""))
                .error("Data Integrity Violation")
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessageResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {

        Map<String, List<ErrorMessageResponse.ValidationError>> validationErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            String fieldName = error.getField();

            ErrorMessageResponse.ValidationError validationError = ErrorMessageResponse.ValidationError.builder()
                    .code(error.getCode())
                    .message(error.getDefaultMessage())
                    .build();
                    
            if (!validationErrors.containsKey(fieldName)) {
                validationErrors.put(fieldName, new ArrayList<>());
            }
            validationErrors.get(fieldName).add(validationError);
        });

        ErrorMessageResponse errorResponse = ErrorMessageResponse.builder()
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Validation failed")
                .path(request.getDescription(false).replace("uri=", ""))
                .validationErrors(validationErrors)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
