package com.vntana.core.rest.client.invitation.user;

import com.vntana.core.model.invitation.user.request.*;
import com.vntana.core.model.invitation.user.response.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 5:42 PM
 */
@FeignClient(name = "coreInvitationUsers", path = "user-invitations", url = "${ms.core.url}")
public interface InvitationUserResourceClient {

    @PostMapping("/organization")
    ResponseEntity<CreateInvitationUserForOrganizationResultResponse> createInvitationForOrganization(@RequestBody final CreateInvitationForOrganizationUserRequest request);

    @PostMapping("/client-organization")
    ResponseEntity<CreateInvitationUserForOrganizationClientsResultResponse> createInvitationForClient(@RequestBody final CreateInvitationForOrganizationClientUserRequest request);

    @PostMapping("/by-status")
    ResponseEntity<GetAllByStatusUserInvitationsResultResponse> getAllByStatus(@RequestBody final GetAllByStatusInvitationUserRequest request);

    @PutMapping("/status")
    ResponseEntity<UpdateInvitationUserInvitationStatusResultResponse> updateStatus(@RequestBody final UpdateInvitationUserInvitationStatusRequest request);

    @PostMapping("/send-invitation/organization")
    ResponseEntity<SendInvitationUserResultResponse> sendInvitation(@RequestBody SendInvitationForOrganizationUserRequest request);

    @PostMapping("/send-invitation/organization-client")
    ResponseEntity<SendInvitationUserResultResponse> sendInvitationForClients(@RequestBody SendInvitationForClientUserRequest request);

    @PostMapping(path = "accept")
    ResponseEntity<AcceptInvitationUserResultResponse> accept(@RequestBody final AcceptInvitationUserRequest request);

    @PostMapping(path = "accept-sign-up")
    ResponseEntity<AcceptInvitationUserResultResponse> acceptAndSignUp(@RequestBody final AcceptInvitationUserAndSignUpRequest request);

    @GetMapping(path = "/by-token")
    ResponseEntity<GetByUserInvitationTokenResultResponse> getByToken(@RequestParam("token") final String token);
}