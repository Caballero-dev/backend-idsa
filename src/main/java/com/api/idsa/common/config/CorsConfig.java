package com.api.idsa.common.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Value("${cors.allowed-origins}")
    private String[] allowedOrigins;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration publicConfig = new CorsConfiguration();
        publicConfig.setAllowedOrigins(Arrays.asList(allowedOrigins));
        publicConfig.setAllowedMethods(Arrays.asList( "POST"));
        publicConfig.setAllowedHeaders(Arrays.asList("Content-Type"));
        publicConfig.setMaxAge(3600L);

        CorsConfiguration protectedConfig = new CorsConfiguration();
        protectedConfig.setAllowedOrigins(Arrays.asList(allowedOrigins));
        protectedConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        protectedConfig.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        protectedConfig.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/auth/**", publicConfig);
        source.registerCorsConfiguration("/api/admin/**", protectedConfig);
        source.registerCorsConfiguration("/api/common/**", protectedConfig);
        source.registerCorsConfiguration("/api/image/**", protectedConfig);
        return source;
    }

}
