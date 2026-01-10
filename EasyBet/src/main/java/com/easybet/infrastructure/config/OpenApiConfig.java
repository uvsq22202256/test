package com.easybet.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Swagger/OpenAPI
 * Infrastructure Layer
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Easy-Bet Casino API",
                version = "1.0",
                description = "API REST pour le casino en ligne Easy-Bet - Projet Clean Architecture ESIEA"
        )
)
public class OpenApiConfig {
}
