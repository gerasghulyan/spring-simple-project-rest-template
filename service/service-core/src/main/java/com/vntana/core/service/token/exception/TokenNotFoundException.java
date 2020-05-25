package com.vntana.core.service.token.exception;

/**
 * Created by Manuk Gharslyan.
 * Date: 4/9/2020
 * Time: 2:12 PM
 */
public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException(final String message) {
        super(message);
    }
}