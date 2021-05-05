package com.vntana.core.model.storage.client.error;

import com.vntana.commons.api.model.response.ErrorResponseModel;

/**
 * Created by Geras Ghulyan
 * Date: 03-May-21
 * Time: 17:24
 */
public enum StorageClientOrganizationKeyErrorResponseModel implements ErrorResponseModel {
    MISSING_NAME,
    MISSING_CLIENT_UUID,
    NOT_FOUND
}
