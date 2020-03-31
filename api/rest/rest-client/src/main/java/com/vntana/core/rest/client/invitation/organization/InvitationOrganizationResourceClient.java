package com.vntana.core.rest.client.invitation.organization;

import com.vntana.core.model.invitation.organization.request.CreateInvitationOrganizationRequest;
import com.vntana.core.model.invitation.organization.request.SendInvitationOrganizationRequest;
import com.vntana.core.model.invitation.organization.response.CreateInvitationOrganizationResponse;
import com.vntana.core.model.invitation.organization.response.GetInvitationOrganizationResponse;
import com.vntana.core.model.invitation.organization.response.SendInvitationOrganizationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 12:38 PM
 */
@FeignClient(name = "coreInvitationOrganizations", path = "organization-invitations", url = "${ms.core.url}")
public interface InvitationOrganizationResourceClient {

    @PostMapping
    ResponseEntity<CreateInvitationOrganizationResponse> create(@RequestBody final CreateInvitationOrganizationRequest request);

    @GetMapping(path = "/{uuid}")
    ResponseEntity<GetInvitationOrganizationResponse> getByUuid(@PathVariable("uuid") final String uuid);

    @PostMapping("/send-invitation")
    ResponseEntity<SendInvitationOrganizationResponse> sendInvitation(@RequestBody SendInvitationOrganizationRequest request);
}
