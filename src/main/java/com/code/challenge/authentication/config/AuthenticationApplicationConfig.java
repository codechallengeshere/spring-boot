package com.code.challenge.authentication.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AuthenticationApplicationConfig {

    @Primary
    @Bean
    protected ObjectMapper getObjectMapper() {
        return JsonMapper.builder()
                .findAndAddModules()
                .build();
    }
}
