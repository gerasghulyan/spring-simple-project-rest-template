package com.vntana.core.rest.client.invitation.organization;

import com.vntana.core.model.invitation.organization.request.*;
import com.vntana.core.model.invitation.organization.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping(path = "/status")
    ResponseEntity<UpdateInvitationOrganizationStatusResponse> updateStatus(@RequestBody final UpdateInvitationOrganizationStatusRequest request);

    @PutMapping(path = "/reject")
    ResponseEntity<RejectInvitationOrganizationResponse> reject(@RequestBody final RejectInvitationOrganizationRequest request);

    @PutMapping(path = "/accept")
    ResponseEntity<AcceptInvitationOrganizationResponse> accept(@RequestBody final AcceptInvitationOrganizationRequest request);

    @PutMapping(path = "/accept-sign-up")
    ResponseEntity<AcceptInvitationOrganizationResponse> acceptAndSignUp(@RequestBody final AcceptAndSignUpInvitationOrganizationRequest request);
}
