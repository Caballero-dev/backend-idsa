package com.api.idsa.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ErrorMessageResponse {

    private String timestamp;
    private int statusCode;
    private String status;
    private String error;
    private String message;
    private String path;

    @Builder.Default
    private List<ValidationError> validationErrors = new ArrayList<>();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValidationError {
        private String code;
        private String field;
        private String message;
    }

}
