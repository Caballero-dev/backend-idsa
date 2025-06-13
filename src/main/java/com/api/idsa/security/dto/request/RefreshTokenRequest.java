package com.api.idsa.security.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {

    @NotBlank(message = "Access token cannot be blank")
    private String accessToken;

    @NotBlank(message = "Refresh token cannot be blank")
    private String refreshToken;
    
}
