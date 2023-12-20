package com.code.challenge.authentication.config;

import co.com.bancolombia.datamask.databind.MaskingObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AuthenticationApplicationConfig {

    @Getter
    private static ObjectMapper maskingObjectMapper;

    @Primary
    @Bean
    protected ObjectMapper getPrimaryObjectMapper() {
        return JsonMapper.builder()
                .findAndAddModules()
                .build();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        createMaskingObjectMapper();

        return maskingObjectMapper;
    }

    private static void createMaskingObjectMapper() {
        maskingObjectMapper = new MaskingObjectMapper();
    }
}
