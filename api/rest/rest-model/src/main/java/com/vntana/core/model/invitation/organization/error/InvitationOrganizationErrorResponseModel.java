package com.vntana.core.model.invitation.organization.error;

import com.vntana.commons.api.model.response.ErrorResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 12:08 PM
 */
public enum InvitationOrganizationErrorResponseModel implements ErrorResponseModel {
    MISSING_UUID,
    MISSING_TOKEN,
    MISSING_OWNER_FULL_NAME,
    MISSING_EMAIL,
    MISSING_ORGANIZATION_NAME,
    MISSING_ORGANIZATION_SLUG,
    MISSING_SLUG,
    MISSING_CUSTOMER_SUBSCRIPTION_DEFINITION_UUID,
    MISSING_INVITATION_STATUS,
    INVALID_SLUG,
    INVALID_TOKEN,
    SLUG_IS_NOT_AVAILABLE,
    NOT_FOUND,
    TOKEN_NOT_FOUND, 
    ALREADY_REJECTED_INVITATION,
    ALREADY_ACCEPTED_INVITATION,
    MISSING_USER_UUID,
    USER_NOT_FOUND,
    TOKEN_IS_EXPIRED,
    MISSING_USER_FULL_NAME,
    MISSING_USER_PASSWORD,
    USER_ALREADY_EXISTS_FOR_EMAIL,
}
