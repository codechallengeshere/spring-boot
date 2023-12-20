package com.code.challenge.authentication.handler;

import com.code.challenge.authentication.dto.ErrorResponse;
import com.code.challenge.authentication.exception.BaseApplicationException;
import com.code.challenge.authentication.exception.CustomerNotFoundException;
import com.code.challenge.authentication.exception.CustomerNotUpdatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundExceptionHandler(CustomerNotFoundException customerNotFoundException) {
        var httpStatus = customerNotFoundException.getHttpStatus();

        var errorResponse = getErrorResponse(customerNotFoundException, httpStatus);

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(CustomerNotUpdatedException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotUpdatedExceptionHandler(CustomerNotUpdatedException customerNotUpdatedException) {
        var httpStatus = customerNotUpdatedException.getHttpStatus();

        var errorResponse = getErrorResponse(customerNotUpdatedException, httpStatus);

        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    private ErrorResponse getErrorResponse(BaseApplicationException applicationException, HttpStatus httpStatus) {
        var errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(applicationException.getErrorCode());
        errorResponse.setErrorMessage(applicationException.getErrorMessage());
        errorResponse.setStatusCode(httpStatus.value());

        return errorResponse;
    }
}
