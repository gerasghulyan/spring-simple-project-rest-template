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
    MISSING_SLUG,
    MISSING_CUSTOMER_SUBSCRIPTION_DEFINITION_UUID,
    INVALID_SLUG,
    INVALID_TOKEN,
    SLUG_IS_NOT_AVAILABLE,
    NOT_FOUND
}
