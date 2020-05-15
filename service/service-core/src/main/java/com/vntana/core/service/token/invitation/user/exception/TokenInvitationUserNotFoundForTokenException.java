package com.vntana.core.service.token.invitation.user.exception;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 6:15 PM
 */
public class TokenInvitationUserNotFoundForTokenException extends RuntimeException {

    private final String token;

    public TokenInvitationUserNotFoundForTokenException(final String token) {
        super(String.format("TokenInvitationUser not found for token %s", token));
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}

