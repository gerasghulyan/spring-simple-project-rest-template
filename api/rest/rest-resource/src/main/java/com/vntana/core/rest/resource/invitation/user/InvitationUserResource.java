package com.vntana.core.rest.resource.invitation.user;

import com.vntana.commons.web.utils.ResponseEntityUtils;
import com.vntana.core.model.invitation.user.request.CreateInvitationUserRequest;
import com.vntana.core.model.invitation.user.response.CreateInvitationUserResponse;
import com.vntana.core.rest.facade.invitation.user.InvitationUserServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 1:39 PM
 */
@RestController
@RequestMapping(value = "/user-invitations", produces = "application/json")
public class InvitationUserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationUserResource.class);

    private final InvitationUserServiceFacade invitationUserServiceFacade;

    public InvitationUserResource(final InvitationUserServiceFacade invitationUserServiceFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.invitationUserServiceFacade = invitationUserServiceFacade;
    }

    @PostMapping
    public ResponseEntity<CreateInvitationUserResponse> create(@RequestBody final CreateInvitationUserRequest request) {
        LOGGER.debug("Processing InvitationUserResource create method for request - {}", request);
        final CreateInvitationUserResponse resultResponse = invitationUserServiceFacade.create(request);
        LOGGER.debug("Successfully processed InvitationUserResource create method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }
}