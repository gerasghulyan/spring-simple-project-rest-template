package com.vntana.core.service.user.exception;

import com.vntana.core.domain.user.User;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Geras Ghulyan.
 * Date: 11/11/19
 * Time: 10:57 AM
 */
public class UserNotFoundForTokenException extends RuntimeException {

    private final String token;

    public UserNotFoundForTokenException(final String token) {
        super(String.format("No entity for token - %s for class - %s was found", token, User.class));
        this.token = token;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("token", token)
                .toString();
    }

    public String getToken() {
        return token;
    }
}
