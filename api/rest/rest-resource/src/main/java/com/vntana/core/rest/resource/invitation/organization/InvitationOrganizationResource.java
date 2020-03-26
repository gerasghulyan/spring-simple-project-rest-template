package com.vntana.core.rest.resource.invitation.organization;

import com.vntana.commons.web.utils.ResponseEntityUtils;
import com.vntana.core.model.invitation.organization.request.CreateInvitationOrganizationRequest;
import com.vntana.core.model.invitation.organization.response.CreateInvitationOrganizationResultResponse;
import com.vntana.core.model.invitation.organization.response.GetInvitationOrganizationResultResponse;
import com.vntana.core.rest.facade.invitation.organization.InvitationOrganizationServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 12:30 PM
 */
@RestController
@RequestMapping(value = "/organization-invitations", produces = "application/json")
public class InvitationOrganizationResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationOrganizationResource.class);

    private final InvitationOrganizationServiceFacade invitationOrganizationServiceFacade;

    public InvitationOrganizationResource(final InvitationOrganizationServiceFacade invitationOrganizationServiceFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.invitationOrganizationServiceFacade = invitationOrganizationServiceFacade;
    }

    @PostMapping
    public ResponseEntity<CreateInvitationOrganizationResultResponse> create(@RequestBody final CreateInvitationOrganizationRequest request) {
        LOGGER.debug("Processing InvitationOrganizationResource create method for request - {}", request);
        final CreateInvitationOrganizationResultResponse resultResponse = invitationOrganizationServiceFacade.create(request);
        LOGGER.debug("Successfully processed InvitationOrganizationResource create method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<GetInvitationOrganizationResultResponse> getByUuid(@PathVariable("uuid") final String uuid) {
        LOGGER.debug("Processing InvitationOrganizationResource getByUuid method for uuid - {}", uuid);
        final GetInvitationOrganizationResultResponse resultResponse = invitationOrganizationServiceFacade.getByUuid(uuid);
        LOGGER.debug("Successfully processed InvitationOrganizationResource getByUuid method for uuid - {}", uuid);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

}
