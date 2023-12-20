package com.code.challenge.authentication.dto;

import co.com.bancolombia.datamask.Mask;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordResponse {

    @Mask(leftVisible = 21)
    @JsonProperty(value = "token")
    private String token;
}
