package com.code.challenge.authentication.helper;

import com.code.challenge.authentication.config.AuthenticationApplicationConfig;
import com.code.challenge.authentication.exception.ApplicationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import static com.code.challenge.authentication.constant.ApplicationErrorCode.ERROR_CODE__INTERNAL_SERVER_ERROR;
import static com.code.challenge.authentication.constant.ApplicationErrorMessage.ERROR_MESSAGE__INTERNAL_SERVER_ERROR;

@Slf4j
@UtilityClass
public class ApplicationHelper {

    public static String convertObjectToJsonString(Object object) {
        log.trace("ApplicationHelper#convertObjectToJsonString: start");

        String result;

        try {
            result = AuthenticationApplicationConfig.getMaskingObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException jsonProcessingException) {
            throw new ApplicationException(
                    ERROR_CODE__INTERNAL_SERVER_ERROR,
                    ERROR_MESSAGE__INTERNAL_SERVER_ERROR,
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        log.trace("ApplicationHelper#convertObjectToJsonString: end");
        return result;
    }
}
