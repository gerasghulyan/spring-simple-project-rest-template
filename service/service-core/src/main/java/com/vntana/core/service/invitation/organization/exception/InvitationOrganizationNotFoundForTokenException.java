package com.vntana.core.service.invitation.organization.exception;

import com.vntana.core.domain.invitation.organization.InvitationOrganization;
import com.vntana.core.service.common.exception.EntityNotFoundForTokenException;

/**
 * Created by Geras Ghulyan.
 * Date: 3/25/20
 * Time: 4:00 PM
 */
public class InvitationOrganizationNotFoundForTokenException extends EntityNotFoundForTokenException {

    public InvitationOrganizationNotFoundForTokenException(final String token) {
        super(token, InvitationOrganization.class);
    }
}
