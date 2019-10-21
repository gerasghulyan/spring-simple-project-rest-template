package com.vntana.core.model.token.error;

import com.vntana.commons.api.model.response.ErrorResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 3:19 PM
 */
public enum TokenErrorResponseModel implements ErrorResponseModel {
    MISSING_EMAIL,
    USER_NOT_FOUND
}
