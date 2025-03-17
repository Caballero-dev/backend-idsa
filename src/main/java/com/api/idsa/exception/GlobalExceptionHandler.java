package com.api.idsa.exception;

import com.api.idsa.dto.response.ErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessageResponse errorMessageResponse = ErrorMessageResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND.getReasonPhrase())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))
                .message(ex.getMessage())
                .description(request.getDescription(false))
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
                .description(request.getDescription(false))
                .error("Duplicate Resource")
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessageResponse);
    }

}
