package com.vntana.core.model.token.error;

import com.vntana.commons.api.model.response.ErrorResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 3:19 PM
 */
public enum TokenErrorResponseModel implements ErrorResponseModel {
    MISSING_TOKEN,
    MISSING_EMAIL,
    MISSING_INVITATION_ORGANIZATION_UUID,
    MISSING_INVITATION_USER_UUID,
    USER_NOT_FOUND,
    TOKEN_NOT_FOUND,
    INVITATION_ORGANIZATION_NOT_FOUND,
    INVITATION_USER_NOT_FOUND
}
