package com.vntana.core.model.client.error;

import com.vntana.core.api.models.response.error.ErrorResponseModel;

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 2:56 PM
 */
public enum ClientOrganizationErrorResponseModel implements ErrorResponseModel {
    MISSING_SLUG,
    SLUG_ALREADY_EXISTS
}
