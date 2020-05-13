package com.vntana.core.rest.client.invitation.user;

import com.vntana.core.model.invitation.user.request.CreateInvitationUserRequest;
import com.vntana.core.model.invitation.user.response.CreateInvitationUserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 5:42 PM
 */
@FeignClient(name = "coreInvitationUsers", path = "user-invitations", url = "${ms.core.url}")
public interface InvitationUserResourceClient {

    @PostMapping
    ResponseEntity<CreateInvitationUserResponse> create(@RequestBody final CreateInvitationUserRequest request);
}