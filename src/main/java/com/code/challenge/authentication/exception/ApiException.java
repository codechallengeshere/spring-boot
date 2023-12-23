package com.code.challenge.authentication.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends BaseException {

    public ApiException(String errorCode, String errorMessage, HttpStatus httpStatus) {
        super(errorCode, errorMessage, httpStatus);
    }
}
