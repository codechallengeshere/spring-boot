package com.code.challenge.authentication.controller;

import com.code.challenge.authentication.dto.ErrorResponse;
import com.code.challenge.authentication.dto.ResetPasswordRequest;
import com.code.challenge.authentication.dto.ResetPasswordResponse;
import com.code.challenge.authentication.exception.CustomerNotFoundException;
import com.code.challenge.authentication.exception.CustomerNotUpdatedException;
import com.code.challenge.authentication.service.ResetPasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Reset Password", description = "Endpoints for reset password")
@Validated
@RestController
@RequestMapping(
        value = "/v1",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ResetPasswordController {

    private final ResetPasswordService resetPasswordService;

    @Autowired
    public ResetPasswordController(ResetPasswordService resetPasswordService) {
        this.resetPasswordService = resetPasswordService;
    }

    @Operation(
            summary = "Reset password",
            description = "Reset password using customer email"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful operation",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ResetPasswordResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Request with not valid email.",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Customer with given email does not exist.",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ErrorResponse.class
                                    )
                            )
                    )
            }
    )
    @PostMapping(value = "/reset-password")
    public ResponseEntity<ResetPasswordResponse> resetPassword(
            @Valid
            @RequestBody
            ResetPasswordRequest resetPasswordRequest
    ) throws CustomerNotFoundException, CustomerNotUpdatedException {
        var resetPasswordResponse = resetPasswordService.resetPassword(resetPasswordRequest);

        return new ResponseEntity<>(resetPasswordResponse, HttpStatus.OK);
    }
}
