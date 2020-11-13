package com.vntana.core.service.invitation.user.exception;

import com.vntana.core.domain.invitation.user.InvitationOrganizationUser;
import com.vntana.commons.service.exception.EntityNotFoundForUuidException;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 12:38 PM
 */
public class InvitationUserNotFoundForUuidException extends EntityNotFoundForUuidException {

    public InvitationUserNotFoundForUuidException(final String uuid) {
        super(uuid, InvitationOrganizationUser.class);
    }
}