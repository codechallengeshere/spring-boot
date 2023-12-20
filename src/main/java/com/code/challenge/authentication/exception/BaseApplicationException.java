package com.code.challenge.authentication.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseApplicationException extends Exception {

    protected final String errorCode;
    protected final String errorMessage;
    protected final HttpStatus httpStatus;

    BaseApplicationException(String errorCode, String errorMessage, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
