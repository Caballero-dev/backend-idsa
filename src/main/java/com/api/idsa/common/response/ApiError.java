package com.api.idsa.common.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private String timestamp;
    private String status;
    private int statusCode;
    private String message;
    private String path;
    private Map<String, List<ValidationError>> validationErrors;

    public ApiError(HttpStatus status, String message, String path) {
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        this.status = status.name();
        this.statusCode = status.value();
        this.message = message;
        this.path = path;
    }

    public void addValidationError(String field, String code, String message) {
        if (validationErrors == null) {
            validationErrors = new HashMap<>();
        }

        ValidationError validationError = ValidationError.builder()
                .code(code)
                .message(message)
                .build();

        if (!validationErrors.containsKey(field)) {
            validationErrors.put(field, new ArrayList<ValidationError>());
        }
        validationErrors.get(field).add(validationError);
    }
    
}
