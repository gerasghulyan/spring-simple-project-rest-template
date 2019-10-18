package com.vntana.core.rest.client.client;

import com.vntana.core.model.client.request.CheckAvailableClientOrganizationSlugRequest;
import com.vntana.core.model.client.response.CheckAvailableClientOrganizationSlugResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 2:49 PM
 */
@FeignClient(name = "coreClients", path = "clients", url = "${ms.core.url}")
public interface ClientOrganizationResourceClient {
    @PostMapping(path = "/slug-availability")
    CheckAvailableClientOrganizationSlugResultResponse createUser(@RequestBody final CheckAvailableClientOrganizationSlugRequest request);
}
