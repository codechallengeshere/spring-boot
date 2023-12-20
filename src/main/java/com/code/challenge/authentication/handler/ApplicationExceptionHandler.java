package com.code.challenge.authentication.handler;

import com.code.challenge.authentication.dto.ApiErrorResponse;
import com.code.challenge.authentication.exception.*;
import com.code.challenge.authentication.helper.ApplicationHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static com.code.challenge.authentication.constant.ApplicationErrorCode.ERROR_CODE__VALIDATION_ERROR;
import static com.code.challenge.authentication.constant.ApplicationErrorMessage.ERROR_MESSAGE__MISSING_REQUIRED_PARAMS;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(SendMailException.class)
    public ResponseEntity<ApiErrorResponse> handleSendMailException(SendMailException sendMailException) {
        log.debug("ResetPasswordController#handleSendMailException: start " + ApplicationHelper.convertObjectToJsonString(sendMailException));

        var httpStatus = sendMailException.getHttpStatus();

        var apiErrorResponse = getApiErrorResponse(sendMailException);

        log.debug("ResetPasswordController#handleSendMailException: end " + ApplicationHelper.convertObjectToJsonString(apiErrorResponse));
        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleCustomerNotFoundExceptionHandler(CustomerNotFoundException customerNotFoundException) {
        var httpStatus = customerNotFoundException.getHttpStatus();

        var apiErrorResponse = getApiErrorResponse(customerNotFoundException);

        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }

    @ExceptionHandler(CustomerNotUpdatedException.class)
    public ResponseEntity<ApiErrorResponse> handleCustomerNotUpdatedExceptionHandler(CustomerNotUpdatedException customerNotUpdatedException) {
        var httpStatus = customerNotUpdatedException.getHttpStatus();

        var apiErrorResponse = getApiErrorResponse(customerNotUpdatedException);

        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadableException(
    ) {
        var httpStatus = HttpStatus.BAD_REQUEST;

        var apiException = new ApiException(
                ERROR_CODE__VALIDATION_ERROR,
                ERROR_MESSAGE__MISSING_REQUIRED_PARAMS,
                httpStatus
        );

        var apiErrorResponse = getApiErrorResponse(apiException);

        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException
    ) throws JsonProcessingException {
        Map<String, String> errors = new HashMap<>();

        methodArgumentNotValidException.getBindingResult()
                .getAllErrors()
                .forEach(
                        error -> {
                            String fieldName = ((FieldError) error).getField();
                            String errorMessage = error.getDefaultMessage();

                            errors.put(fieldName, errorMessage);
                        }
                );

        var httpStatus = HttpStatus.BAD_REQUEST;

        var apiException = new ApiException(
                ERROR_CODE__VALIDATION_ERROR,
                ApplicationHelper.convertObjectToJsonString(errors),
                httpStatus
        );

        var apiErrorResponse = getApiErrorResponse(apiException);

        return new ResponseEntity<>(apiErrorResponse, httpStatus);
    }

    private ApiErrorResponse getApiErrorResponse(BaseApplicationException applicationException) {
        return ApiErrorResponse.builder()
                .errorCode(applicationException.getErrorCode())
                .errorMessage(applicationException.getErrorMessage())
                .statusCode(applicationException.getHttpStatus().value())
                .build();
    }
}
