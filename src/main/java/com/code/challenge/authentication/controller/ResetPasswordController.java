package com.code.challenge.authentication.controller;

import com.code.challenge.authentication.dto.ApiErrorResponse;
import com.code.challenge.authentication.dto.ResetPasswordRequest;
import com.code.challenge.authentication.dto.ResetPasswordResponse;
import com.code.challenge.authentication.exception.CustomerNotFoundException;
import com.code.challenge.authentication.exception.CustomerNotUpdatedException;
import com.code.challenge.authentication.exception.SendMailException;
import com.code.challenge.authentication.helper.ApplicationHelper;
import com.code.challenge.authentication.service.ResetPasswordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.code.challenge.authentication.controller.ResetPasswordController.RESET_PASSWORD_CONTROLLER_ENDPOINT_PREFIX_PATH;

@Slf4j
@Tag(name = "Reset Password", description = "Endpoints for reset password")
@RestController
@RequestMapping(
        value = RESET_PASSWORD_CONTROLLER_ENDPOINT_PREFIX_PATH,
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ResetPasswordController {

    public static final String RESET_PASSWORD_CONTROLLER_ENDPOINT_PREFIX_PATH = "/v1";
    public static final String RESET_PASSWORD_ENDPOINT_PATH = "/reset-password";

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
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ResetPasswordResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Request with not valid email.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ApiErrorResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Customer with given email does not exist.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ApiErrorResponse.class
                                    )
                            )
                    )
            }
    )
    @PostMapping(
            value = RESET_PASSWORD_ENDPOINT_PATH,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResetPasswordResponse> resetPassword(
            @Valid
            @RequestBody
            ResetPasswordRequest resetPasswordRequest
    ) throws CustomerNotFoundException, CustomerNotUpdatedException, SendMailException, JsonProcessingException {
        log.debug("ResetPasswordController#resetPassword: " + ApplicationHelper.convertObjectToJsonString(resetPasswordRequest));

        var resetPasswordResponse = resetPasswordService.resetPassword(resetPasswordRequest);

        log.debug("ResetPasswordController#resetPassword: success");
        return new ResponseEntity<>(resetPasswordResponse, HttpStatus.OK);
    }
}
