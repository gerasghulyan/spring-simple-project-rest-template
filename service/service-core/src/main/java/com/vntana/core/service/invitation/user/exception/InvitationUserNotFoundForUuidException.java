package com.vntana.core.service.invitation.user.exception;

import com.vntana.core.domain.invitation.user.InvitationUser;
import com.vntana.core.service.common.exception.EntityNotFoundForUuidException;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 12:38 PM
 */
public class InvitationUserNotFoundForUuidException extends EntityNotFoundForUuidException {

    public InvitationUserNotFoundForUuidException(final String uuid) {
        super(uuid, InvitationUser.class);
    }
}