package com.code.challenge.authentication.config;

import co.com.bancolombia.datamask.databind.MaskingObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AuthenticationApplicationConfig {

    private static ObjectMapper maskingObjectMapper;

    @Primary
    @Bean
    protected ObjectMapper getObjectMapper() {
        return JsonMapper.builder()
                .findAndAddModules()
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        AuthenticationApplicationConfig.maskingObjectMapper = new MaskingObjectMapper();

        return maskingObjectMapper;
    }

    public static ObjectMapper getMaskingObjectMapper() {
        return AuthenticationApplicationConfig.maskingObjectMapper;
    }
}
