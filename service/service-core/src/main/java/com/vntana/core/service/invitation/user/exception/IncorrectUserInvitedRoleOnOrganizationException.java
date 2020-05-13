package com.vntana.core.service.invitation.user.exception;

import com.vntana.core.domain.user.UserRole;

import static java.lang.String.format;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/13/2020
 * Time: 12:49 PM
 */
public class IncorrectUserInvitedRoleOnOrganizationException extends RuntimeException {

    public IncorrectUserInvitedRoleOnOrganizationException(final UserRole role) {
        super(format("The invited user role on organization could nor be - %s", role));
    }
}