package com.vntana.core.rest.client.invitation;

import com.vntana.core.model.invitation.request.InvitationToPlatformRequest;
import com.vntana.core.model.invitation.response.InvitationToPlatformResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 10:42 AM
 */
@FeignClient(name = "coreInvitations", path = "invitations", url = "${ms.core.url}")
public interface InvitationResourceClient {

    @PostMapping(path = "platform")
    InvitationToPlatformResponse inviteToPlatform(@RequestBody final InvitationToPlatformRequest request);

}
