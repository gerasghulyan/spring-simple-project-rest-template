package com.vntana.core.rest.client.storage.client;

import com.vntana.core.model.storage.client.request.CreateStorageClientOrganizationKeyRequest;
import com.vntana.core.model.storage.client.response.create.CreateStorageClientOrganizationKeyResultResponse;
import com.vntana.core.model.storage.client.response.get.GetStorageClientOrganizationKeyResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Geras Ghulyan
 * Date: 05-May-21
 * Time: 16:58
 */
@FeignClient(name = "coreStorageClientsKey", path = "storage-clients-key", url = "${ms.core.url}")
public interface StorageClientOrganizationKeyResourceClient {

    @PostMapping
    ResponseEntity<CreateStorageClientOrganizationKeyResultResponse> create(@RequestBody final CreateStorageClientOrganizationKeyRequest request);

    @GetMapping(path = "/{clientUuid}")
    ResponseEntity<GetStorageClientOrganizationKeyResultResponse> getByClientUuid(@PathVariable("clientUuid") final String clientUuid);

}
