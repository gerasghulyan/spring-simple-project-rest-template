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

    @PostMapping
    public ResponseEntity<CreateInvitationUserResultResponse> create(@RequestBody final CreateInvitationUserRequest request) {
        LOGGER.debug("Processing InvitationUserResource create method for request - {}", request);
        final CreateInvitationUserResultResponse resultResponse = invitationUserServiceFacade.create(request);
        LOGGER.debug("Successfully processed InvitationUserResource create method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PostMapping(path = "/by-status")
    public ResponseEntity<GetAllByStatusUserInvitationsResultResponse> getAllByStatus(@RequestBody final GetAllByStatusInvitationUserRequest request) {
        LOGGER.debug("Processing InvitationUserResource getAllByStatus method for request - {}", request);
        final GetAllByStatusUserInvitationsResultResponse resultResponse = invitationUserServiceFacade.getAllByStatus(request);
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

    @PostMapping(path = "accept")
    public ResponseEntity<AcceptInvitationUserResultResponse> accept(@RequestBody final AcceptInvitationUserRequest request) {
        LOGGER.debug("Processing InvitationUserResource acceptInvitation method for request - {}", request);
        final AcceptInvitationUserResultResponse resultResponse = invitationUserServiceFacade.accept(request);
        LOGGER.debug("Successfully processed InvitationUserResource acceptInvitation method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PostMapping(path = "accept-sign-up")
    public ResponseEntity<AcceptInvitationUserResultResponse> acceptAndSignUp(@RequestBody final AcceptInvitationUserAndSignUpRequest request) {
        LOGGER.debug("Processing InvitationUserResource acceptAndSignUp method for request - {}", request);
        final AcceptInvitationUserResultResponse resultResponse = invitationUserServiceFacade.acceptAndSignUp(request);
        LOGGER.debug("Successfully processed InvitationUserResource acceptAndSignUp method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @PostMapping("/send-invitation")
    public ResponseEntity<SendInvitationUserResultResponse> sendInvitation(@RequestBody final SendInvitationUserRequest request) {
        LOGGER.debug("Processing InvitationUserResource sendInvitation method for request - {}", request);
        final SendInvitationUserResultResponse resultResponse = invitationUserServiceFacade.sendInvitation(request);
        LOGGER.debug("Successfully processed InvitationUserResource sendInvitation method for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }

    @GetMapping(path = "/by-token")
    public ResponseEntity<GetByUserInvitationTokenResultResponse> getByToken(@RequestParam("token") final String token) {
        LOGGER.debug("Processing InvitationUserResource getByToken method");
        final GetByUserInvitationTokenResultResponse resultResponse = invitationUserServiceFacade.getByToken(token);
        LOGGER.debug("Successfully processed InvitationUserResource getByToken method");
        return ResponseEntityUtils.okWithStatusInHeader(resultResponse);
    }
}