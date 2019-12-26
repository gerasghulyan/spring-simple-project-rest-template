package com.vntana.core.rest.resource.invitation;

import com.vntana.core.model.invitation.request.InvitationToPlatformRequest;
import com.vntana.core.model.invitation.response.InvitationToPlatformResponse;
import com.vntana.core.rest.facade.invitation.InvitationServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 10:33 AM
 */
@RestController
@RequestMapping(path = "/invitations", produces = "application/json")
public class InvitationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationResource.class);

    private final InvitationServiceFacade invitationServiceFacade;

    public InvitationResource(final InvitationServiceFacade invitationServiceFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.invitationServiceFacade = invitationServiceFacade;
    }

    @PostMapping(path = "platform")
    public ResponseEntity<InvitationToPlatformResponse> inviteToPlatform(@RequestBody final InvitationToPlatformRequest request) {
        LOGGER.debug("Processing invitation resource inviteToPlatform methods for request - {}", request);
        final InvitationToPlatformResponse response = invitationServiceFacade.inviteToPlatform(request);
        LOGGER.debug("Successfully processed invitation resource inviteToPlatform methods for request - {}", request);
        return ResponseEntity.ok(response);
    }
}
