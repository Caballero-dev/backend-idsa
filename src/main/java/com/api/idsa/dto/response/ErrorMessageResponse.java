package com.api.idsa.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessageResponse {

    private String timestamp;
    private int statusCode;
    private String status;
    private String error;
    private String message;
    private String description;

}
