package com.vntana.core.rest.client.organization;

import com.vntana.core.model.organization.request.CheckAvailableOrganizationSlugRequest;
import com.vntana.core.model.organization.request.CreateOrganizationRequest;
import com.vntana.core.model.organization.response.CheckAvailableOrganizationSlugResultResponse;
import com.vntana.core.model.organization.response.CreateOrganizationResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 3:44 PM
 */
@FeignClient(name = "coreOrganizations", path = "organizations", url = "${ms.core.url}")
public interface OrganizationResourceClient {
    @PostMapping(path = "/slug-availability")
    CheckAvailableOrganizationSlugResultResponse checkSlugAvailability(@RequestBody final CheckAvailableOrganizationSlugRequest request);

    @PostMapping(path = "/create")
    CreateOrganizationResultResponse create(@RequestBody final CreateOrganizationRequest request);
}
