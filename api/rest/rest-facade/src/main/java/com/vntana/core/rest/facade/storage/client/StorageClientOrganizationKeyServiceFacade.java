package com.vntana.core.rest.facade.storage.client;

import com.vntana.core.model.storage.client.request.CreateStorageClientOrganizationKeyRequest;
import com.vntana.core.model.storage.client.response.create.CreateStorageClientOrganizationKeyResultResponse;
import com.vntana.core.model.storage.client.response.get.GetStorageClientOrganizationKeyResultResponse;

/**
 * Created by Geras Ghulyan
 * Date: 03-May-21
 * Time: 17:19
 */
public interface StorageClientOrganizationKeyServiceFacade {

    CreateStorageClientOrganizationKeyResultResponse create(final CreateStorageClientOrganizationKeyRequest request);

    GetStorageClientOrganizationKeyResultResponse getByClientUuid(final String clientUuid);
    
}
