package com.vntana.core.model.invitation.user.error;

import com.vntana.commons.api.model.response.ErrorResponseModel;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 1:44 PM
 */
public enum InvitationUserErrorResponseModel implements ErrorResponseModel {
    MISSING_USER_ROLE,
    MISSING_INVITED_USER_EMAIL,
    MISSING_INVITER_USER_UUID,
    MISSING_INVITING_ORGANIZATION_UUID,
    MISSING_INVITING_ORGANIZATION_NAME,
    MISSING_USER_FULL_NAME,
    MISSING_PASSWORD,
    TOKEN_IS_EXPIRED,
    MISSING_INVITATION_STATUS,
    MISSING_UUID,
    MISSING_INVITATION_TOKEN,
    MISSING_INVITATION_TOKENS,
    INVITATION_NOT_FOUND,
    INVITER_USER_NOT_FOUND,
    INVITED_USER_ROLE_COULD_NOT_BE_ORGANIZATION_OWNER,
    INVITING_ORGANIZATION_NOT_FOUND,
    USER_ALREADY_PART_OF_ORGANIZATION,
    INVALID_INVITATION_TOKEN,
    NOT_FOUND_FOR_TOKEN,
    USER_ALREADY_HAS_ROLE_IN_ORGANIZATION,
    USER_ALREADY_EXISTS,
    MISSING_INVITATIONS,
    USER_ALREADY_HAS_ROLE_IN_CLIENT,
    INVITED_USER_ALREADY_EXISTS_IN_ORGANIZATION,
    INCORRECT_PERMISSIONS
}