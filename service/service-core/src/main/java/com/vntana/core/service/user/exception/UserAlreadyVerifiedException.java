package com.vntana.core.service.user.exception;

/**
 * Created by Arman Gevorgyan.
 * Date: 11/11/19
 * Time: 1:25 PM
 */
public class UserAlreadyVerifiedException extends RuntimeException {

    public UserAlreadyVerifiedException(final String message) {
        super(message);
    }
}
