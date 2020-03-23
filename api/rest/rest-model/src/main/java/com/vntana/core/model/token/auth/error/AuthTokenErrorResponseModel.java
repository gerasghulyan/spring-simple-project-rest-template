package com.vntana.core.model.token.auth.error;

import com.vntana.commons.api.model.response.ErrorResponseModel;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:21 AM
 */
public enum AuthTokenErrorResponseModel implements ErrorResponseModel {
    MISSING_TOKEN,
    MISSING_USER_UUID,
    TOKEN_NOT_FOUND,
    USER_NOT_FOUND
}
