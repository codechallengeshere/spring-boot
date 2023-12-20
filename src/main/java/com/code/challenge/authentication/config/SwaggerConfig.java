package com.code.challenge.authentication.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(
                        new Components()
                )
                .info(
                        new Info()
                                .title("Authentication API")
                                .description("Authentication service API implemented with Spring Boot and Java 17.")
                                .contact(
                                        new Contact()
                                                .name("codechallengeshere")
                                                .url("https://github.com/codechallengeshere")
                                )
                );
    }
}
