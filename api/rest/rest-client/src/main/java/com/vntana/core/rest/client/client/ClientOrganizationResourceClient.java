package com.vntana.core.rest.client.client;

import com.vntana.core.model.client.request.CheckAvailableClientOrganizationSlugRequest;
import com.vntana.core.model.client.request.CreateClientOrganizationRequest;
import com.vntana.core.model.client.request.UpdateClientOrganizationRequest;
import com.vntana.core.model.client.response.CheckAvailableClientOrganizationSlugResultResponse;
import com.vntana.core.model.client.response.CreateClientOrganizationResultResponse;
import com.vntana.core.model.client.response.UpdateClientOrganizationResultResponse;
import com.vntana.core.model.client.response.get.GetAllOrganizationsResultResponse;
import com.vntana.core.model.client.response.get.GetClientOrganizationBySlugResultResponse;
import com.vntana.core.model.client.response.get.GetClientOrganizationResultResponse;
import com.vntana.core.model.user.response.UserClientOrganizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 2:49 PM
 */
@FeignClient(name = "coreClients", path = "clients", url = "${ms.core.url}")
public interface ClientOrganizationResourceClient {
    @PostMapping(path = "/slug-availability")
    CheckAvailableClientOrganizationSlugResultResponse checkSlugAvailability(@RequestBody final CheckAvailableClientOrganizationSlugRequest request);

    @PostMapping
    CreateClientOrganizationResultResponse create(@RequestBody final CreateClientOrganizationRequest request);

    @GetMapping(path = "/users/{userUuid}/organizations/{userOrganizationUuid}")
    UserClientOrganizationResponse getUserClientOrganizations(@PathVariable("userUuid") final String userUuid,
                                                              @PathVariable("userOrganizationUuid") final String userOrganizationUuid);

    @GetMapping(path = "/{uuid}")
    GetClientOrganizationResultResponse getByUuid(@PathVariable("uuid") final String uuid);

    @GetMapping(path = "/organizations/{organizationUuid}/clients/{slug}")
    GetClientOrganizationBySlugResultResponse getBySlug(@PathVariable("organizationUuid") final String organizationUuid, @PathVariable("slug") final String slug);

    @GetMapping(path = "/")
    GetAllOrganizationsResultResponse getAll();

    @PutMapping
    UpdateClientOrganizationResultResponse update(@RequestBody final UpdateClientOrganizationRequest request);
}
