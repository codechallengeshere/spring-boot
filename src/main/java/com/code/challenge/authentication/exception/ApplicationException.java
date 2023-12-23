package com.code.challenge.authentication.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends BaseException {

    public ApplicationException(String errorCode, String errorMessage, HttpStatus httpStatus) {
        super(errorCode, errorMessage, httpStatus);
    }
}
