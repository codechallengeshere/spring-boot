package com.code.challenge.authentication.exception;

import org.springframework.http.HttpStatus;

public class SendMailException extends BaseException {

    public SendMailException(String errorCode, String errorMessage, HttpStatus httpStatus) {
        super(errorCode, errorMessage, httpStatus);
    }
}
