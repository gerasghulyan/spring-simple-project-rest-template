package com.vntana.core.rest.resource.invitation.user;

import com.vntana.commons.web.utils.ResponseEntityUtils;
import com.vntana.core.model.invitation.user.request.*;
import com.vntana.core.model.invitation.user.response.*;
import com.vntana.core.rest.facade.invitation.user.InvitationUserServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/organization")
    public ResponseEntity<CreateInvitationUserForOrganizationResultResponse> createInvitationForOrganization(@RequestBody final CreateInvitationForOrganizationUserRequest request) {
        LOGGER.debug("Processing InvitationUserResource createInvitationForOrganization method for request - {}", request);
        final CreateInvitationUserForOrganizationResultResponse resultResponse = invitationUserServiceFacade.createInvitationForOrganization(request);
        LOGGER.debug("Successfully processed InvitationUserResource createInvitationToOrganization method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PostMapping(path = "/client-organization")
    public ResponseEntity<CreateInvitationUserForOrganizationClientsResultResponse> createInvitationForClient(@RequestBody final CreateInvitationForOrganizationClientUserRequest request) {
        LOGGER.debug("Processing InvitationUserResource createInvitationForClient method for request - {}", request);
        final CreateInvitationUserForOrganizationClientsResultResponse resultResponse = invitationUserServiceFacade.createInvitationForClient(request);
        LOGGER.debug("Successfully processed InvitationUserResource createInvitationToClient method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PostMapping(path = "/by-status")
    public ResponseEntity<GetAllByStatusUserInvitationsResultResponse> getAllByStatus(@RequestBody final GetAllByStatusInvitationUserRequest request) {
        LOGGER.debug("Processing InvitationUserResource getAllByStatus method for request - {}", request);
        final GetAllByStatusUserInvitationsResultResponse resultResponse = invitationUserServiceFacade.getAllInvitationsToOrganizationByOrganizationUuidAndStatus(request);
        LOGGER.debug("Successfully processed InvitationUserResource getAllByStatus method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PutMapping(path = "/status")
    public ResponseEntity<UpdateInvitationUserInvitationStatusResultResponse> updateStatus(@RequestBody final UpdateInvitationUserInvitationStatusRequest request) {
        LOGGER.debug("Processing InvitationUserResource updateStatus method for request - {}", request);
        final UpdateInvitationUserInvitationStatusResultResponse resultResponse = invitationUserServiceFacade.updateStatus(request);
        LOGGER.debug("Successfully processed InvitationUserResource updateStatus method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PostMapping(path = "accept/organization")
    public ResponseEntity<AcceptInvitationUserToOrganizationResultResponse> acceptInvitationForOrganization(@RequestBody final AcceptInvitationUserRequest request) {
        LOGGER.debug("Processing InvitationUserResource acceptInvitationForOrganization method for request - {}", request);
        final AcceptInvitationUserToOrganizationResultResponse resultResponse = invitationUserServiceFacade.acceptInvitationToOrganization(request);
        LOGGER.debug("Successfully processed InvitationUserResource acceptInvitationForOrganization method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PostMapping(path = "accept/client-organization")
    public ResponseEntity<AcceptInvitationUserToClientResultResponse> acceptInvitationForClient(@RequestBody final AcceptInvitationUserRequest request) {
        LOGGER.debug("Processing InvitationUserResource acceptInvitationForClient method for request - {}", request);
        final AcceptInvitationUserToClientResultResponse resultResponse = invitationUserServiceFacade.acceptInvitationToClient(request);
        LOGGER.debug("Successfully processed InvitationUserResource acceptInvitationForClient method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PostMapping(path = "accept-sign-up/organization")
    public ResponseEntity<AcceptInvitationUserToOrganizationResultResponse> acceptAndSignUpForOrganization(@RequestBody final AcceptInvitationUserAndSignUpRequest request) {
        LOGGER.debug("Processing InvitationUserResource acceptAndSignUpForOrganization method for request - {}", request);
        final AcceptInvitationUserToOrganizationResultResponse resultResponse = invitationUserServiceFacade.acceptInvitationToOrganizationAndSignUp(request);
        LOGGER.debug("Successfully processed InvitationUserResource acceptAndSignUpForOrganization method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PostMapping(path = "accept-sign-up/client-organization")
    public ResponseEntity<AcceptInvitationUserToClientResultResponse> acceptAndSignUpForClient(@RequestBody final AcceptInvitationUserAndSignUpRequest request) {
        LOGGER.debug("Processing InvitationUserResource acceptAndSignUpForClient method for request - {}", request);
        final AcceptInvitationUserToClientResultResponse resultResponse = invitationUserServiceFacade.acceptInvitationToClientAndSignUp(request);
        LOGGER.debug("Successfully processed InvitationUserResource acceptAndSignUpForClient method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PostMapping("/send-invitation/organization")
    public ResponseEntity<SendInvitationUserResultResponse> sendInvitationForOrganization(@RequestBody final SendInvitationForOrganizationUserRequest request) {
        LOGGER.debug("Processing InvitationUserResource sendInvitationForOrganization method for request - {}", request);
        final SendInvitationUserResultResponse resultResponse = invitationUserServiceFacade.sendInvitationForOrganization(request);
        LOGGER.debug("Successfully processed InvitationUserResource sendInvitationForOrganization method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PostMapping(("/send-invitation/client-organization"))
    public ResponseEntity<SendInvitationUserResultResponse> sendInvitationForClients(@RequestBody SendInvitationForClientUserRequest request) {
        LOGGER.debug("Processing InvitationUserResource sendInvitationForClient method for request - {}", request);
        final SendInvitationUserResultResponse resultResponse = invitationUserServiceFacade.sendInvitationForClients(request);
        LOGGER.debug("Successfully processed InvitationUserResource sendInvitationForClient method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @GetMapping(path = "/by-token/organization")
    public ResponseEntity<GetByUserInvitationTokenResultResponse> getByTokenInvitationToOrganization(@RequestParam("token") final String token) {
        LOGGER.debug("Processing InvitationUserResource getByTokenInvitationToOrganization method");
        final GetByUserInvitationTokenResultResponse resultResponse = invitationUserServiceFacade.getByTokenInvitationToOrganization(token);
        LOGGER.debug("Successfully processed InvitationUserResource getByTokenInvitationToOrganization method");
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @GetMapping(path = "/by-token/client-organization")
    public ResponseEntity<GetByUserInvitationTokenResultResponse> getByTokenInvitationToClient(@RequestParam("token") final String token) {
        LOGGER.debug("Processing InvitationUserResource getByTokenInvitationToClient method");
        final GetByUserInvitationTokenResultResponse resultResponse = invitationUserServiceFacade.getByTokenInvitationToClient(token);
        LOGGER.debug("Successfully processed InvitationUserResource getByTokenInvitationToClient method");
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }
}