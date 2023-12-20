package com.code.challenge.authentication.service;

import com.code.challenge.authentication.dto.ResetPasswordRequest;
import com.code.challenge.authentication.dto.ResetPasswordResponse;
import com.code.challenge.authentication.exception.CustomerNotFoundException;
import com.code.challenge.authentication.exception.CustomerNotUpdatedException;
import com.code.challenge.authentication.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import static com.code.challenge.authentication.constant.ApplicationErrorCode.ERROR_CODE_CUSTOMER_NOT_FOUND;
import static com.code.challenge.authentication.constant.ApplicationErrorCode.ERROR_CODE_INTERNAL_SERVER_ERROR;
import static com.code.challenge.authentication.constant.ApplicationErrorMessage.ERROR_MESSAGE_CUSTOMER_NOT_FOUND;
import static com.code.challenge.authentication.constant.ApplicationErrorMessage.ERROR_MESSAGE_INTERNAL_SERVER_ERROR;

@Service
public class ResetPasswordService {

    private final CustomerRepository customerRepository;

    @Autowired
    public ResetPasswordService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public ResetPasswordResponse resetPassword(ResetPasswordRequest resetPasswordRequest) throws CustomerNotFoundException, CustomerNotUpdatedException {
        var customer = customerRepository.findCustomerByEmail(resetPasswordRequest.getEmail());
        if (null == customer) {
            throw new CustomerNotFoundException(
                    ERROR_CODE_CUSTOMER_NOT_FOUND,
                    ERROR_MESSAGE_CUSTOMER_NOT_FOUND,
                    HttpStatus.BAD_REQUEST
            );
        }

        String token = BCrypt.hashpw("randomPasswordToken", "randomSalt");

        customer.setEnabled(false);
        customer.setResetPasswordToken(token);

        try {
            customerRepository.save(customer);
        } catch (Exception exception) {
            throw new CustomerNotUpdatedException(
                    ERROR_CODE_INTERNAL_SERVER_ERROR,
                    ERROR_MESSAGE_INTERNAL_SERVER_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        return ResetPasswordResponse.builder()
                .token(token)
                .build();
    }
}
