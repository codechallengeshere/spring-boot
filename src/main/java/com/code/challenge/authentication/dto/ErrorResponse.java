package com.code.challenge.authentication.dto;

import lombok.Data;

@Data
public class ErrorResponse {

    private int statusCode;
    private String errorMessage;
    private String errorCode;
}
