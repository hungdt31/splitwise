package com.splitwise.server.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger UI (springdoc) + mô tả OpenAPI; nút Authorize dùng JWT Bearer.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI eventMateOpenAPI() {
        final String bearer = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("EventMate / Splitwise API")
                        .description("REST API. Login at /auth/login, then paste the accessToken in Authorize.")
                        .version("1.0.0"))
                .addSecurityItem(new SecurityRequirement().addList(bearer))
                .components(new Components().addSecuritySchemes(bearer,
                        new SecurityScheme()
                                .name(bearer)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("JWT from POST /auth/login or /auth/register")));
    }
}
