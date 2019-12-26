package com.vntana.core.rest.facade.invitation.impl;

import com.vntana.core.model.invitation.error.InvitationErrorResponseModel;
import com.vntana.core.model.invitation.request.InvitationToPlatformRequest;
import com.vntana.core.model.invitation.response.InvitationToPlatformResponse;
import com.vntana.core.rest.facade.invitation.InvitationServiceFacade;
import com.vntana.core.rest.facade.invitation.component.PlatformInvitationSenderComponent;
import com.vntana.core.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 10:22 AM
 */
@Component
public class InvitationServiceFacadeImpl implements InvitationServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationServiceFacadeImpl.class);

    private final UserService userService;
    private final PlatformInvitationSenderComponent platformInvitationSenderComponent;

    public InvitationServiceFacadeImpl(final UserService userService, final PlatformInvitationSenderComponent platformInvitationSenderComponent) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userService = userService;
        this.platformInvitationSenderComponent = platformInvitationSenderComponent;
    }

    @Override
    public InvitationToPlatformResponse inviteToPlatform(final InvitationToPlatformRequest request) {
        LOGGER.debug("Processing organization facade invite method for request - {}", request);
        if (userService.findByEmail(request.getEmail()).isPresent()) {
            return new InvitationToPlatformResponse(InvitationErrorResponseModel.USER_ALREADY_EXISTS);
        }
        platformInvitationSenderComponent.sendInvitation(request.getEmail(), request.getToken());
        LOGGER.debug("Successfully processed organization facade invite method for request - {}", request);
        return new InvitationToPlatformResponse();
    }
}
