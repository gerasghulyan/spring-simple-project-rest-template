package com.vntana.core.model.client.error;

import com.vntana.commons.api.model.response.ErrorResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 2:56 PM
 */
public enum ClientOrganizationErrorResponseModel implements ErrorResponseModel {
    MISSING_UUID,
    MISSING_ORGANIZATION_UUID,
    MISSING_NAME,
    MISSING_SLUG,
    SLUG_ALREADY_EXISTS,
    SLUG_NOT_VALID,
    CLIENT_NOT_FOUND,
    USER_NOT_FOUND,
    ORGANIZATION_NOT_FOUND,
    MISSING_USER_UUID
}
