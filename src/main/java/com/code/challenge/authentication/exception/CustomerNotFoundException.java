package com.code.challenge.authentication.exception;

import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends BaseApplicationException {

    public CustomerNotFoundException(String errorCode, String errorMessage, HttpStatus httpStatus) {
        super(errorCode, errorMessage, httpStatus);
    }
}
