package com.vntana.core.rest.facade.storage.client.impl;

import com.vntana.core.domain.storage.client.StorageClientOrganizationKey;
import com.vntana.core.model.storage.client.error.StorageClientOrganizationKeyErrorResponseModel;
import com.vntana.core.model.storage.client.request.CreateStorageClientOrganizationKeyRequest;
import com.vntana.core.model.storage.client.response.create.CreateStorageClientOrganizationKeyResponseModel;
import com.vntana.core.model.storage.client.response.create.CreateStorageClientOrganizationKeyResultResponse;
import com.vntana.core.model.storage.client.response.get.GetStorageClientOrganizationKeyResponseModel;
import com.vntana.core.model.storage.client.response.get.GetStorageClientOrganizationKeyResultResponse;
import com.vntana.core.rest.facade.storage.client.StorageClientOrganizationKeyServiceFacade;
import com.vntana.core.service.storage.client.StorageClientOrganizationKeyService;
import com.vntana.core.service.storage.client.dto.CreateStorageClientOrganizationKeyDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Created by Geras Ghulyan
 * Date: 03-May-21
 * Time: 17:34
 */
@Component
public class StorageClientOrganizationKeyServiceFacadeImpl implements StorageClientOrganizationKeyServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageClientOrganizationKeyServiceFacadeImpl.class);

    private final StorageClientOrganizationKeyService storageClientOrganizationKeyService;

    public StorageClientOrganizationKeyServiceFacadeImpl(final StorageClientOrganizationKeyService storageClientOrganizationKeyService) {
        this.storageClientOrganizationKeyService = storageClientOrganizationKeyService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public CreateStorageClientOrganizationKeyResultResponse create(final CreateStorageClientOrganizationKeyRequest request) {
        LOGGER.debug("Creating StorageClientOrganizationKey by clientUuid - {}", request.getClientUuid());
        final StorageClientOrganizationKey key = storageClientOrganizationKeyService.create(
                new CreateStorageClientOrganizationKeyDto(request.getClientUuid())
        );
        LOGGER.debug("Successfully created StorageClientOrganizationKey by clientUuid - {}", request.getClientUuid());
        return new CreateStorageClientOrganizationKeyResultResponse(new CreateStorageClientOrganizationKeyResponseModel(key.getClientOrganization().getUuid()));
    }

    @Override
    public GetStorageClientOrganizationKeyResultResponse getByClientUuid(final String clientUuid) {
        if (StringUtils.isBlank(clientUuid)) {
            return new GetStorageClientOrganizationKeyResultResponse(HttpStatus.SC_UNPROCESSABLE_ENTITY, Collections.singletonList(StorageClientOrganizationKeyErrorResponseModel.MISSING_CLIENT_UUID));
        }
        LOGGER.debug("Retrieving StorageClientOrganizationKey by clientUuid - {}", clientUuid);
        return storageClientOrganizationKeyService.findByClientOrganization(clientUuid)
                .map(key -> {
                    LOGGER.debug("Successfully retrieved StorageClientOrganizationKey by clientUuid - {}", clientUuid);
                    return new GetStorageClientOrganizationKeyResultResponse(
                            new GetStorageClientOrganizationKeyResponseModel(key.getClientOrganization().getUuid(), key.getName())
                    );
                })
                .orElseGet(() -> {
                    LOGGER.debug("Can not find StorageClientOrganizationKey by clientUuid - {}", clientUuid);
                    return new GetStorageClientOrganizationKeyResultResponse(
                            HttpStatus.SC_NOT_FOUND, Collections.singletonList(StorageClientOrganizationKeyErrorResponseModel.NOT_FOUND)
                    );
                });
    }
}
