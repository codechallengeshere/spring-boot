package com.code.challenge.authentication.service;

import co.com.bancolombia.datamask.MaskUtils;
import com.code.challenge.authentication.dto.ResetPasswordRequest;
import com.code.challenge.authentication.dto.ResetPasswordResponse;
import com.code.challenge.authentication.entity.Customer;
import com.code.challenge.authentication.exception.CustomerNotFoundException;
import com.code.challenge.authentication.exception.CustomerNotUpdatedException;
import com.code.challenge.authentication.exception.SendMailException;
import com.code.challenge.authentication.helper.ApplicationHelper;
import com.code.challenge.authentication.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static com.code.challenge.authentication.constant.ApplicationErrorCode.ERROR_CODE__CUSTOMER_NOT_FOUND;
import static com.code.challenge.authentication.constant.ApplicationErrorCode.ERROR_CODE__INTERNAL_SERVER_ERROR;
import static com.code.challenge.authentication.constant.ApplicationErrorMessage.ERROR_MESSAGE__CUSTOMER_NOT_FOUND;
import static com.code.challenge.authentication.constant.ApplicationErrorMessage.ERROR_MESSAGE__INTERNAL_SERVER_ERROR;

@Slf4j
@Service
public class ResetPasswordService {

    private final CustomerRepository customerRepository;
    private final EmailService emailService;

    @Autowired
    public ResetPasswordService(
            CustomerRepository customerRepository,
            EmailService emailService
    ) {
        this.customerRepository = customerRepository;
        this.emailService = emailService;
    }

    public ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest) throws CustomerNotFoundException, CustomerNotUpdatedException, SendMailException {
        log.debug("ResetPasswordService#resetPassword: start: " + ApplicationHelper.convertObjectToJsonString(resetPasswordRequest));

        var customer = getCustomer(resetPasswordRequest);

        String resetPasswordToken = BCrypt.hashpw(RandomStringUtils.random(5), BCrypt.gensalt());
        var instant = Instant.now().plusSeconds(60 * 60 * 3);
        var tokenExpiresAt = Date.from(instant);

        customer.setResetPasswordTokenExpiresAt(tokenExpiresAt);
        customer.setResetPasswordToken(resetPasswordToken);
        customer.setEnabled(false);

        saveCustomer(customer);

        sendMail(customer, resetPasswordToken);

        var resetPasswordResponse = ResetPasswordResponse.builder()
                .token(resetPasswordToken)
                .build();

        log.debug("ResetPasswordService#resetPassword: end: " + ApplicationHelper.convertObjectToJsonString(resetPasswordResponse));
        return resetPasswordResponse;
    }

    private void sendMail(Customer customer, String resetPasswordToken) throws SendMailException {
        var paramsToLog = Map.of(
                "customer", customer,
                "resetPasswordToken", MaskUtils.mask(resetPasswordToken, 21, 0)
        );

        log.debug("ResetPasswordService#sendMail: start: " + ApplicationHelper.convertObjectToJsonString(paramsToLog));

        try {
            emailService.sendResetPasswordTokenMail(
                    customer.getEmail(),
                    resetPasswordToken
            );
        } catch (MailException mailException) {
            log.warn("ResetPasswordService#sendMail: MailException:" + mailException);

            throw new SendMailException(
                    ERROR_CODE__INTERNAL_SERVER_ERROR,
                    ERROR_MESSAGE__INTERNAL_SERVER_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        log.debug("ResetPasswordService#sendMail: end");
    }

    private Customer getCustomer(ResetPasswordRequest resetPasswordRequest) throws CustomerNotFoundException {
        log.debug("ResetPasswordService#getCustomer: " + ApplicationHelper.convertObjectToJsonString(resetPasswordRequest));

        var customer = customerRepository.findCustomerByEmail(resetPasswordRequest.getEmail());

        if (customer == null) {
            log.warn("ResetPasswordService#getCustomer: CustomerNotFoundException");

            throw new CustomerNotFoundException(
                    ERROR_CODE__CUSTOMER_NOT_FOUND,
                    ERROR_MESSAGE__CUSTOMER_NOT_FOUND,
                    HttpStatus.NOT_FOUND
            );
        }

        log.debug("ResetPasswordService#getCustomer: end: " + ApplicationHelper.convertObjectToJsonString(customer));
        return customer;
    }

    private void saveCustomer(Customer customer) throws CustomerNotUpdatedException {
        log.debug("ResetPasswordService#saveCustomer: end: " + ApplicationHelper.convertObjectToJsonString(customer));

        try {
            customerRepository.save(customer);
        } catch (Exception exception) {
            log.warn("ResetPasswordService#saveCustomer: Exception: " + exception);

            throw new CustomerNotUpdatedException(
                    ERROR_CODE__INTERNAL_SERVER_ERROR,
                    ERROR_MESSAGE__INTERNAL_SERVER_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        log.debug("ResetPasswordService#saveCustomer: end: " + ApplicationHelper.convertObjectToJsonString(customer));
    }
}
