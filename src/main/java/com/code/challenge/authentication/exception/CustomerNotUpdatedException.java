package com.code.challenge.authentication.exception;

import org.springframework.http.HttpStatus;

public class CustomerNotUpdatedException extends BaseApplicationException {

    public CustomerNotUpdatedException(String errorCode, String errorMessage, HttpStatus httpStatus) {
        super(errorCode, errorMessage, httpStatus);
    }
}
