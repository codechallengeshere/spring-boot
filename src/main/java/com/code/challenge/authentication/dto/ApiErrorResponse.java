package com.code.challenge.authentication.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {

    @JsonIgnore
    @JsonProperty(value = "status_code")
    private int statusCode;

    @JsonProperty(value = "error_message")
    private String errorMessage;

    @JsonProperty(value = "error_code")
    private String errorCode;
}
