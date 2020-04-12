package com.vntana.core.rest.resource.invitation.organization;

import com.vntana.commons.web.utils.ResponseEntityUtils;
import com.vntana.core.model.invitation.organization.request.*;
import com.vntana.core.model.invitation.organization.response.*;
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
    public ResponseEntity<CreateInvitationOrganizationResponse> create(@RequestBody final CreateInvitationOrganizationRequest request) {
        LOGGER.debug("Processing InvitationOrganizationResource create method for request - {}", request);
        final CreateInvitationOrganizationResponse resultResponse = invitationOrganizationServiceFacade.create(request);
        LOGGER.debug("Successfully processed InvitationOrganizationResource create method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PostMapping("/send-invitation")
    public ResponseEntity<SendInvitationOrganizationResponse> sendInvitation(@RequestBody final SendInvitationOrganizationRequest request) {
        LOGGER.debug("Processing InvitationOrganizationResource sendInvitation method for request - {}", request);
        final SendInvitationOrganizationResponse resultResponse = invitationOrganizationServiceFacade.sendInvitation(request);
        LOGGER.debug("Successfully processed InvitationOrganizationResource sendInvitation method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<GetInvitationOrganizationResponse> getByUuid(@PathVariable("uuid") final String uuid) {
        LOGGER.debug("Processing InvitationOrganizationResource getByUuid method for uuid - {}", uuid);
        final GetInvitationOrganizationResponse resultResponse = invitationOrganizationServiceFacade.getByUuid(uuid);
        LOGGER.debug("Successfully processed InvitationOrganizationResource getByUuid method for uuid - {}", uuid);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PutMapping(path = "/status")
    public ResponseEntity<UpdateInvitationOrganizationStatusResponse> updateStatus(@RequestBody final UpdateInvitationOrganizationStatusRequest request) {
        LOGGER.debug("Processing InvitationOrganizationResource updateStatus method for request - {}", request);
        final UpdateInvitationOrganizationStatusResponse resultResponse = invitationOrganizationServiceFacade.updateStatus(request);
        LOGGER.debug("Successfully processed InvitationOrganizationResource updateStatus method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PutMapping(path = "/reject")
    public ResponseEntity<RejectInvitationOrganizationResponse> reject(@RequestBody final RejectInvitationOrganizationRequest request) {
        LOGGER.debug("Processing InvitationOrganizationResource reject method for request - {}", request);
        final RejectInvitationOrganizationResponse resultResponse = invitationOrganizationServiceFacade.reject(request);
        LOGGER.debug("Successfully processed InvitationOrganizationResource reject method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PutMapping(path = "/accept")
    public ResponseEntity<AcceptInvitationOrganizationResponse> accept(@RequestBody final AcceptInvitationOrganizationRequest request) {
        LOGGER.debug("Processing InvitationOrganizationResource accept method for request - {}", request);
        final AcceptInvitationOrganizationResponse resultResponse = invitationOrganizationServiceFacade.accept(request);
        LOGGER.debug("Successfully processed InvitationOrganizationResource accept method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }
}
