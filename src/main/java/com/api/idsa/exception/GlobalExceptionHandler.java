package com.api.idsa.exception;

import com.api.idsa.dto.response.ErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, WebRequest request) {

        List<ErrorMessageResponse.ValidationError> validationErrors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = "";
            String rejectedValue = "";

            if (error instanceof FieldError) {
                fieldName = ((FieldError) error).getField();
                rejectedValue = ((FieldError) error).getRejectedValue() != null ?
                        ((FieldError) error).getRejectedValue().toString() : "";
            }

            validationErrors.add(
                    ErrorMessageResponse.ValidationError.builder()
                            .code(error.getCode())
                            .field(fieldName)
                            .message(error.getDefaultMessage())
                            .rejectedValue(rejectedValue)
                            .build()
            );
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
