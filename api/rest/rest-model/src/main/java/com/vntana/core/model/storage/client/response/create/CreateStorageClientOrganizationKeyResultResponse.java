package com.vntana.core.model.storage.client.response.create;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.storage.client.error.StorageClientOrganizationKeyErrorResponseModel;

import java.util.List;

/**
 * Created by Geras Ghulyan
 * Date: 03-May-21
 * Time: 17:24
 */
public class CreateStorageClientOrganizationKeyResultResponse extends AbstractResultResponseModel<CreateStorageClientOrganizationKeyResponseModel, StorageClientOrganizationKeyErrorResponseModel> {

    public CreateStorageClientOrganizationKeyResultResponse() {
    }

    public CreateStorageClientOrganizationKeyResultResponse(final int httpStatusCode, final List<StorageClientOrganizationKeyErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public CreateStorageClientOrganizationKeyResultResponse(final CreateStorageClientOrganizationKeyResponseModel response, final int httpStatusCode) {
        super(response, httpStatusCode);
    }

    public CreateStorageClientOrganizationKeyResultResponse(final CreateStorageClientOrganizationKeyResponseModel response) {
        super(response);
    }
}
