package com.restaurant.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Restaurant Order Management API")
                        .description("REST API for managing restaurant table orders. Supports table setup, order creation, item management, and order closing with automatic total calculation.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Restaurant API")
                                .email("api@restaurant.com")));
    }
}
