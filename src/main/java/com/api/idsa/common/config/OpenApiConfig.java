package com.api.idsa.common.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	@Value("${server.port:8080}")
	private String serverPort;

	@Value("${spring.application.name:idsa}")
	private String applicationName;

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
					.title("API del Sistema IDSA")
					.description("Sistema integral para la identificación de síntomas de consumo de sustancias adictivas. " +
							"Esta API permite la gestión de usuarios, estudiantes, grupos académicos, " +
							"análisis biométricos y generación de reportes mediante tecnologías IoT, " +
							"análisis de imágenes y modelos de Deep Learning.")
					.version("1.4.0")
				)
				.servers(List.of(
						new Server()
								.url("http://localhost:" + serverPort)
								.description("Servidor de desarrollo local")
					)
				)
				.addSecurityItem(new SecurityRequirement()
					.addList("Bearer Authentication")
				)
				.components(new Components()
					.addSecuritySchemes("Bearer Authentication", new SecurityScheme()
							.type(SecurityScheme.Type.HTTP)
							.scheme("bearer")
							.bearerFormat("JWT")
							.description("Ingresa tu token JWT para autenticarte. Puedes obtenerlo mediante el endpoint de login.")
					)
				);
	}
}