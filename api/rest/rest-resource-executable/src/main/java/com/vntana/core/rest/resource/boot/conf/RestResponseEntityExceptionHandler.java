package com.vntana.core.rest.resource.boot.conf;

import com.vntana.commons.api.model.constants.Headers;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.UUID;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/4/19
 * Time: 2:59 PM
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<Object> handle(final RuntimeException e, final WebRequest request) {
        final String uuid = UUID.randomUUID().toString();
        final String responseBody = String.format("Error occurred having uuid - %s", uuid);
        LOGGER.error("{} Stacktrace {}", uuid, ExceptionUtils.getStackTrace(e));
        final HttpHeaders headers = new HttpHeaders();
        headers.put(Headers.MS_ERRORS, Collections.singletonList(responseBody));
        return handleExceptionInternal(e, responseBody, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}