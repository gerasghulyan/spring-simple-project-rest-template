package com.vntana.core.service.invitation.user.exception;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 6:15 PM
 */
public class InvitationUserNotFoundForTokenException extends RuntimeException {

    private final String token;

    public InvitationUserNotFoundForTokenException(final String token) {
        super(String.format("InvitationUser not found for token %s", token));
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}