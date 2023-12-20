package com.code.challenge.authentication.dto;

import co.com.bancolombia.datamask.Mask;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {

    @Mask(isEmail = true)
    @NotBlank
    @Email(message = "Not valid email.")
    @JsonProperty(value = "email")
    private String email;
}
