package com.vntana.core.rest.client.client;

import com.vntana.core.model.client.request.CheckAvailableClientOrganizationSlugRequest;
import com.vntana.core.model.client.request.CreateClientOrganizationRequest;
import com.vntana.core.model.client.response.CheckAvailableClientOrganizationSlugResultResponse;
import com.vntana.core.model.client.response.CreateClientOrganizationResultResponse;
import com.vntana.core.model.user.response.UserClientOrganizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    CheckAvailableClientOrganizationSlugResultResponse checkSlugAvailability(@RequestBody final CheckAvailableClientOrganizationSlugRequest request);

    @PostMapping(path = "/create")
    CreateClientOrganizationResultResponse create(@RequestBody final CreateClientOrganizationRequest request);

    @GetMapping(path = "/users/{userUuid}/organizations/{userOrganizationUuid}")
    UserClientOrganizationResponse getUserClientOrganizations(@PathVariable("userUuid") final String userUuid,
                                                              @PathVariable("userOrganizationUuid") final String userOrganizationUuid);
}
