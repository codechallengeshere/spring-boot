package com.code.challenge.authentication.integration;

import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@Getter
@EnableAutoConfiguration(
        exclude = {
        }
)
@TestPropertySource(
        properties = {
                "mocking.wiremock.enabled = false"
        }
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest
public abstract class BaseAuthenticationApplicationTest {

    @BeforeEach
    void beforeEach() {

    }

    @AfterEach
    void afterEach() {

    }
}
