package com.vntana.core.rest.client.organization;

import com.vntana.core.model.organization.request.CheckAvailableOrganizationSlugRequest;
import com.vntana.core.model.organization.request.CreateOrganizationRequest;
import com.vntana.core.model.organization.response.CheckAvailableOrganizationSlugResultResponse;
import com.vntana.core.model.organization.response.create.CreateOrganizationResultResponse;
import com.vntana.core.model.organization.response.get.GetOrganizationBySlugResultResponse;
import com.vntana.core.model.organization.response.get.GetOrganizationByUuidResultResponse;
import com.vntana.core.model.organization.response.invitation.GetOrganizationInvitationByOrganizationResponse;
import com.vntana.core.model.organization.response.update.request.UpdateOrganizationRequest;
import com.vntana.core.model.organization.response.update.response.UpdateOrganizationResultResponse;
import com.vntana.core.model.user.response.UserOrganizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/users/{uuid}")
    UserOrganizationResponse getUserOrganizations(@PathVariable("uuid") final String uuid);

    @GetMapping(path = "/slug/{slug}")
    GetOrganizationBySlugResultResponse getBySlug(@PathVariable("slug") final String slug);

    @GetMapping(path = "/{uuid}")
    GetOrganizationByUuidResultResponse getByUuid(@PathVariable("uuid") final String uuid);

    @PutMapping
    UpdateOrganizationResultResponse update(@RequestBody final UpdateOrganizationRequest request);

    @GetMapping(path = "{organizationUuid}/organization-invitations/")
    ResponseEntity<GetOrganizationInvitationByOrganizationResponse> getOrganizationInvitation(@PathVariable("organizationUuid") final String organizationUuid);
}
