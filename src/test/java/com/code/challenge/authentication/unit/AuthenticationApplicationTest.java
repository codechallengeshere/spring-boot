package com.code.challenge.authentication.unit;

import com.code.challenge.authentication.config.MailPropertiesConfig;
import com.code.challenge.authentication.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.IOException;

import static org.mockito.Mockito.mock;

public abstract class AuthenticationApplicationTest {

    @Mock
    protected CustomerRepository customerRepositoryMock;

    @Mock
    protected JavaMailSender javaMailSenderMock;

    @Mock
    protected MailPropertiesConfig mailPropertiesConfigMock;

    @BeforeEach
    protected void beforeEach() throws IOException {
        this.customerRepositoryMock = mock(CustomerRepository.class);
        this.javaMailSenderMock = mock(JavaMailSender.class);
        this.mailPropertiesConfigMock = mock(MailPropertiesConfig.class);
    }

    @AfterEach
    void afterEach() {
        // no-op
    }
}
