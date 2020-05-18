package com.vntana.core.rest.client.invitation.user;

import com.vntana.core.model.invitation.user.request.AcceptInvitationUserRequest;
import com.vntana.core.model.invitation.user.request.CreateInvitationUserRequest;
import com.vntana.core.model.invitation.user.request.GetAllByStatusInvitationUserRequest;
import com.vntana.core.model.invitation.user.request.SendInvitationUserRequest;
import com.vntana.core.model.invitation.user.request.UpdateInvitationUserInvitationStatusRequest;
import com.vntana.core.model.invitation.user.response.AcceptInvitationUserResultResponse;
import com.vntana.core.model.invitation.user.response.CreateInvitationUserResultResponse;
import com.vntana.core.model.invitation.user.response.GetAllByStatusUserInvitationsResultResponse;
import com.vntana.core.model.invitation.user.response.SendInvitationUserResultResponse;
import com.vntana.core.model.invitation.user.response.UpdateInvitationUserInvitationStatusResultResponse;
import com.vntana.core.model.invitation.user.response.GetByUserInvitationTokenResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 5:42 PM
 */
@FeignClient(name = "coreInvitationUsers", path = "user-invitations", url = "${ms.core.url}")
public interface InvitationUserResourceClient {

    @PostMapping
    ResponseEntity<CreateInvitationUserResultResponse> create(@RequestBody final CreateInvitationUserRequest request);

    @PostMapping("/by-status")
    ResponseEntity<GetAllByStatusUserInvitationsResultResponse> getAllByStatus(@RequestBody final GetAllByStatusInvitationUserRequest request);

    @PutMapping("/status")
    ResponseEntity<UpdateInvitationUserInvitationStatusResultResponse> updateStatus(@RequestBody final UpdateInvitationUserInvitationStatusRequest request);

    @PostMapping("/send-invitation")
    ResponseEntity<SendInvitationUserResultResponse> sendInvitation(@RequestBody SendInvitationUserRequest request);

    @PostMapping(path = "accept")
    ResponseEntity<AcceptInvitationUserResultResponse> accept(@RequestBody final AcceptInvitationUserRequest request);

    @GetMapping(path = "/by-token")
    ResponseEntity<GetByUserInvitationTokenResultResponse> getByToken(@RequestParam("token") final String token);
}