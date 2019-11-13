package com.vntana.core.model.client.error;

import com.vntana.commons.api.model.response.ErrorResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 2:56 PM
 */
public enum ClientOrganizationErrorResponseModel implements ErrorResponseModel {
    MISSING_ORGANIZATION_UUID,
    MISSING_NAME,
    MISSING_SLUG,
    SLUG_ALREADY_EXISTS,
    ORGANIZATION_NOT_FOUND
}
