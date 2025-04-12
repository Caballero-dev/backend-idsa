package com.api.idsa.common.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationError {
    private String code;
    private String message;
}
