package com.code.challenge.authentication.exception;

import org.springframework.http.HttpStatus;

public class CustomerNotUpdatedException extends BaseException {

    public CustomerNotUpdatedException(String errorCode, String errorMessage, HttpStatus httpStatus) {
        super(errorCode, errorMessage, httpStatus);
    }
}
