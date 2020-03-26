package com.vntana.core.service.invitation.organization.exception;

import com.vntana.core.domain.invitation.organization.InvitationOrganization;
import com.vntana.core.service.common.exception.EntityNotFoundForUuidException;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/25/20
 * Time: 4:00 PM
 */
public class InvitationOrganizationNotFoundForUuidException extends EntityNotFoundForUuidException {

    public InvitationOrganizationNotFoundForUuidException(final String uuid) {
        super(uuid, InvitationOrganization.class);
    }
}
