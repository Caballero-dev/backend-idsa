package com.api.idsa.common.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    
    private String status;
    private int statusCode;
    private String message;
    private T data;
    private PageInfo pageInfo;

    public ApiResponse(HttpStatus status, String message, T data) {
        this.status = status.name();
        this.statusCode = status.value();
        this.message = message;
        this.data = data;
    }
    
    public ApiResponse(HttpStatus status, String message, T data, PageInfo pageInfo) {
        this(status, message, data);
        this.pageInfo = pageInfo;
    }
    
}
