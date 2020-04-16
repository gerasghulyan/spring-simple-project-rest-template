package com.vntana.core.rest.facade.common.exception;

/**
 * Created by Geras Ghulyan
 * Date: 11.04.20
 * Time: 17:23
 */
public class ServiceFacadeRuntimeException extends RuntimeException {
    
    public ServiceFacadeRuntimeException(final String message) {
        super(message);
    }

    public ServiceFacadeRuntimeException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    public ServiceFacadeRuntimeException(final Throwable throwable) {
        super(throwable);
    }
}
