package com.vntana.core.model.organization.error;

import com.vntana.commons.api.model.response.ErrorResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 2:56 PM
 */
public enum OrganizationErrorResponseModel implements ErrorResponseModel {
    MISSING_NAME,
    MISSING_SLUG,
    SLUG_ALREADY_EXISTS,
    SLUG_NOT_FOUND,
    MISSING_USER_UUID
}
