package com.vntana.core.model.storage.client.response.get;

import com.vntana.commons.api.model.response.impl.AbstractResultResponseModel;
import com.vntana.core.model.storage.client.error.StorageClientOrganizationKeyErrorResponseModel;

import java.util.List;

/**
 * Created by Geras Ghulyan
 * Date: 03-May-21
 * Time: 17:24
 */
public class GetStorageClientOrganizationKeyResultResponse extends AbstractResultResponseModel<GetStorageClientOrganizationKeyResponseModel, StorageClientOrganizationKeyErrorResponseModel> {

    public GetStorageClientOrganizationKeyResultResponse() {
    }

    public GetStorageClientOrganizationKeyResultResponse(final int httpStatusCode, final List<StorageClientOrganizationKeyErrorResponseModel> errors) {
        super(httpStatusCode, errors);
    }

    public GetStorageClientOrganizationKeyResultResponse(final GetStorageClientOrganizationKeyResponseModel response, final int httpStatusCode) {
        super(response, httpStatusCode);
    }

    public GetStorageClientOrganizationKeyResultResponse(final GetStorageClientOrganizationKeyResponseModel response) {
        super(response);
    }
}
