package com.code.challenge.authentication.unit.service;

import com.code.challenge.authentication.dto.ResetPasswordRequest;
import com.code.challenge.authentication.entity.Customer;
import com.code.challenge.authentication.service.EmailService;
import com.code.challenge.authentication.service.ResetPasswordService;
import com.code.challenge.authentication.unit.AuthenticationApplicationTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

class ResetPasswordServiceTest extends AuthenticationApplicationTest {

    private ResetPasswordService resetPasswordService;

    @Override
    @BeforeEach
    protected void beforeEach() throws IOException {
        super.beforeEach();

        var emailService = new EmailService(
                mailPropertiesConfigMock,
                javaMailSenderMock
        );

        this.resetPasswordService = new ResetPasswordService(
                customerRepositoryMock,
                emailService
        );
    }

    @Test
    void success() {
        String email = "active@user.local";
        String password = BCrypt.hashpw(RandomStringUtils.random(5), BCrypt.gensalt());

        var customerStub = Customer.builder()
                .email(email)
                .firstName("first name")
                .lastName("last name")
                .password(password)
                .isEnabled(true)
                .resetPasswordToken(null)
                .build();

        doReturn(customerStub)
                .when(customerRepositoryMock)
                .findCustomerByEmail(any());

        String resetPasswordToken = BCrypt.hashpw(RandomStringUtils.random(5), BCrypt.gensalt());
        var instant = Instant.now().plusSeconds(60 * 60 * 3);
        var tokenExpiresAt = Date.from(instant);

        var updatedCustomerStub = Customer.builder()
                .email(email)
                .firstName("first name")
                .lastName("last name")
                .password(password)
                .isEnabled(false)
                .resetPasswordToken(resetPasswordToken)
                .resetPasswordTokenExpiresAt(tokenExpiresAt)
                .build();

        doReturn(updatedCustomerStub)
                .when(customerRepositoryMock)
                .save(any());

        doNothing()
                .when(javaMailSenderMock)
                .send(any(SimpleMailMessage.class));

        var resetPasswordRequest = ResetPasswordRequest.builder()
                .email(email)
                .build();

        var resetPasswordResponse = resetPasswordService.resetPassword(resetPasswordRequest);
        assertFalse(resetPasswordResponse.getToken().isEmpty());
    }
}
