package com.code.challenge.authentication.integration.controller;

import com.code.challenge.authentication.dto.ResetPasswordRequest;
import com.code.challenge.authentication.entity.Customer;
import com.code.challenge.authentication.integration.AuthenticationApplicationIT;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import static com.code.challenge.authentication.controller.ResetPasswordController.RESET_PASSWORD_CONTROLLER_ENDPOINT_PREFIX_PATH;
import static com.code.challenge.authentication.controller.ResetPasswordController.RESET_PASSWORD_ENDPOINT_PATH;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ResetPasswordControllerIT extends AuthenticationApplicationIT {

    @Test
    void success() throws Exception {
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
                .when(customerRepositoryMockBean)
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
                .when(customerRepositoryMockBean)
                .save(any());

        doNothing()
                .when(javaMailSenderMockBean)
                .send(any(SimpleMailMessage.class));

        var resetPasswordRequest = ResetPasswordRequest.builder()
                .email(email)
                .build();

        String url = RESET_PASSWORD_CONTROLLER_ENDPOINT_PREFIX_PATH + RESET_PASSWORD_ENDPOINT_PATH;
        var requestBuilder = post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetPasswordRequest).getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }
}
