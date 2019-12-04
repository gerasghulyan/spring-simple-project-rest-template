package com.vntana.core.rest.resource.boot.conf;

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

import java.util.UUID;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/4/19
 * Time: 2:59 PM
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> handle(final RuntimeException e, final WebRequest request) {
        final String uuid = UUID.randomUUID().toString();
        final String responseBody = String.format("Error occurred having uuid - %s", uuid);
        LOGGER.error("Exception caught having uuid - {} and stacktrace - {}", uuid, ExceptionUtils.getStackTrace(e));
        return handleExceptionInternal(e, responseBody, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}