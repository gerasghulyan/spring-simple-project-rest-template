package com.vntana.core.rest.resource.storage.client;

import com.vntana.commons.web.utils.ResponseEntityUtils;
import com.vntana.core.model.storage.client.request.CreateStorageClientOrganizationKeyRequest;
import com.vntana.core.model.storage.client.response.create.CreateStorageClientOrganizationKeyResultResponse;
import com.vntana.core.model.storage.client.response.get.GetStorageClientOrganizationKeyResultResponse;
import com.vntana.core.rest.facade.storage.client.StorageClientOrganizationKeyServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Geras Ghulyan
 * Date: 05-May-21
 * Time: 16:52
 */
@RestController
@RequestMapping(value = "/storage-clients-key", produces = MediaType.APPLICATION_JSON_VALUE)
public class StorageClientOrganizationKeyResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageClientOrganizationKeyResource.class);

    private final StorageClientOrganizationKeyServiceFacade storageClientOrganizationKeyServiceFacade;

    public StorageClientOrganizationKeyResource(final StorageClientOrganizationKeyServiceFacade storageClientOrganizationKeyServiceFacade) {
        this.storageClientOrganizationKeyServiceFacade = storageClientOrganizationKeyServiceFacade;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @PostMapping
    public ResponseEntity<CreateStorageClientOrganizationKeyResultResponse> create(@RequestBody final CreateStorageClientOrganizationKeyRequest request) {
        LOGGER.debug("Processing StorageClientOrganizationKeyResource create method for request - {}", request);
        final CreateStorageClientOrganizationKeyResultResponse response = storageClientOrganizationKeyServiceFacade.create(request);
        LOGGER.debug("Successfully processed StorageClientOrganizationKeyResource create method with response - {}", response);
        return ResponseEntityUtils.okWithStatusInHeader(response);
    }

    @GetMapping(path = "/{clientUuid}")
    public ResponseEntity<GetStorageClientOrganizationKeyResultResponse> getByClientUuid(@PathVariable("clientUuid") final String clientUuid) {
        LOGGER.debug("Processing StorageClientOrganizationKeyResource getByClientUuid method for clientUuid - {}", clientUuid);
        final GetStorageClientOrganizationKeyResultResponse response = storageClientOrganizationKeyServiceFacade.getByClientUuid(clientUuid);
        LOGGER.debug("Successfully processed StorageClientOrganizationKeyResource getByClientUuid method with response - {}", response);
        return ResponseEntityUtils.okWithStatusInHeader(response);
    }
}
