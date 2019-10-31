package com.vntana.core.model.user.error;

import com.vntana.commons.api.model.response.ErrorResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/11/19
 * Time: 4:05 PM
 */
public enum UserErrorResponseModel implements ErrorResponseModel {
    MISSING_CLIENT_NAME,
    MISSING_CLIENT_SLUG,
    MISSING_FULL_NAME,
    MISSING_EMAIL,
    MISSING_PASSWORD,
    MISSING_ORGANIZATION,
    NOT_FOUND_FOR_EMAIL,
    NOT_FOUND_FOR_ORGANIZATION,
    NOT_FOUND_FOR_ROLE,
}
