package com.api.idsa.common.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class ErrorMessageResponse {

    private String timestamp;
    private int statusCode;
    private String status;
    private String error;
    private String message;
    private String path;

    private Map<String, List<ValidationError>> validationErrors;

    @Data
    @Builder
    public static class ValidationError {
        private String code;
        private String message;
    }

}
