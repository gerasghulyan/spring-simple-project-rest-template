package com.vntana.core.model.invitation.user.error;

import com.vntana.commons.api.model.response.ErrorResponseModel;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 1:44 PM
 */
public enum InvitationUserErrorResponseModel implements ErrorResponseModel {
    MISSING_USER_ROLE,
    MISSING_USER_EMAIL,
    MISSING_INVITER_USER_UUID,
    MISSING_INVITING_ORGANIZATION_UUID,
    INVITER_USER_NOT_FOUND,
    INVITING_ORGANIZATION_NOT_FOUND,
    USER_ALREADY_PART_OF_ORGANIZATION
}