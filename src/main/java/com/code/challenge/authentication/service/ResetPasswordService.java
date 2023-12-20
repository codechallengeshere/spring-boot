package com.code.challenge.authentication.service;

import com.code.challenge.authentication.dto.ResetPasswordRequest;
import com.code.challenge.authentication.dto.ResetPasswordResponse;
import com.code.challenge.authentication.entity.Customer;
import com.code.challenge.authentication.exception.CustomerNotFoundException;
import com.code.challenge.authentication.exception.CustomerNotUpdatedException;
import com.code.challenge.authentication.exception.SendMailException;
import com.code.challenge.authentication.repository.CustomerRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import static com.code.challenge.authentication.constant.ApplicationErrorCode.ERROR_CODE__CUSTOMER_NOT_FOUND;
import static com.code.challenge.authentication.constant.ApplicationErrorCode.ERROR_CODE__INTERNAL_SERVER_ERROR;
import static com.code.challenge.authentication.constant.ApplicationErrorMessage.ERROR_MESSAGE__CUSTOMER_NOT_FOUND;
import static com.code.challenge.authentication.constant.ApplicationErrorMessage.ERROR_MESSAGE__INTERNAL_SERVER_ERROR;

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
        String resetPasswordToken = BCrypt.hashpw(RandomStringUtils.random(5), BCrypt.gensalt());

        var customer = getCustomer(resetPasswordRequest);
        customer.setResetPasswordToken(resetPasswordToken);
        customer.setEnabled(false);

        saveCustomer(customer);

        sendMail(customer, resetPasswordToken);

        return ResetPasswordResponse.builder()
                .token(resetPasswordToken)
                .build();
    }

    private void sendMail(Customer customer, String resetPasswordToken) throws SendMailException {
        try {
            emailService.sendEmail(
                    customer.getEmail(),
                    "reset password token",
                    "reset password token: " + resetPasswordToken
            );
        } catch (MailException mailException) {
            throw new SendMailException(
                    ERROR_CODE__INTERNAL_SERVER_ERROR,
                    ERROR_MESSAGE__INTERNAL_SERVER_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    private Customer getCustomer(ResetPasswordRequest resetPasswordRequest) throws CustomerNotFoundException {
        var customer = customerRepository.findCustomerByEmail(resetPasswordRequest.getEmail());

        if (customer == null) {
            throw new CustomerNotFoundException(
                    ERROR_CODE__CUSTOMER_NOT_FOUND,
                    ERROR_MESSAGE__CUSTOMER_NOT_FOUND,
                    HttpStatus.NOT_FOUND
            );
        }

        return customer;
    }

    private void saveCustomer(Customer customer) throws CustomerNotUpdatedException {
        try {
            customerRepository.save(customer);
        } catch (Exception exception) {
            throw new CustomerNotUpdatedException(
                    ERROR_CODE__INTERNAL_SERVER_ERROR,
                    ERROR_MESSAGE__INTERNAL_SERVER_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
