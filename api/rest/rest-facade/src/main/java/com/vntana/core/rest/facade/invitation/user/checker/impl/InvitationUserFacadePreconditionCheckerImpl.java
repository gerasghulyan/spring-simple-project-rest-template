package com.vntana.core.rest.facade.invitation.user.checker.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import com.vntana.core.model.invitation.user.request.CreateInvitationUserRequest;
import com.vntana.core.model.invitation.user.request.UpdateInvitationUserInvitationStatusRequest;
import com.vntana.core.rest.facade.invitation.user.checker.InvitationUserFacadePreconditionChecker;
import com.vntana.core.service.invitation.user.InvitationUserService;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 1:55 PM
 */
@Component
public class InvitationUserFacadePreconditionCheckerImpl implements InvitationUserFacadePreconditionChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationUserFacadePreconditionCheckerImpl.class);

    private final UserService userService;
    private final OrganizationService organizationService;
    private final InvitationUserService invitationUserService;

    public InvitationUserFacadePreconditionCheckerImpl(final UserService userService,
                                                       final OrganizationService organizationService,
                                                       final InvitationUserService invitationUserService) {
        this.userService = userService;
        this.organizationService = organizationService;
        this.invitationUserService = invitationUserService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkCreateForPossibleErrors(final CreateInvitationUserRequest request) {
        LOGGER.debug("Checking invitation user creation precondition for request - {}", request);
        if (UserRoleModel.ORGANIZATION_OWNER == request.getUserRole()) {
            LOGGER.error("Checking invitation user creation precondition for request - {} has been done with error, the invited role could not be organization owner", request);
            return SingleErrorWithStatus.of(HttpStatus.SC_CONFLICT, InvitationUserErrorResponseModel.INVITED_USER_ROLE_COULD_NOT_BE_ORGANIZATION_OWNER);
        }
        if (!userService.existsByUuid(request.getInviterUserUuid())) {
            LOGGER.error("Checking invitation user creation precondition for request - {} has been done with error, no inviter user was found by uuid - {}", request, request.getInviterUserUuid());
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND);
        }
        if (!organizationService.existsByUuid(request.getOrganizationUuid())) {
            LOGGER.error("Checking invitation user creation precondition for request - {} has been done with error, no organization was found by uuid - {}", request, request.getOrganizationUuid());
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.INVITING_ORGANIZATION_NOT_FOUND);
        }
        if (userService.findByEmailAndOrganizationUuid(request.getEmail(), request.getOrganizationUuid()).isPresent()) {
            LOGGER.error("Checking invitation user creation precondition for request - {} has been done with error, the invited user already is part of the organization", request);
            return SingleErrorWithStatus.of(HttpStatus.SC_CONFLICT, InvitationUserErrorResponseModel.USER_ALREADY_PART_OF_ORGANIZATION);
        }
        LOGGER.debug("Successfully checked invitation user creation precondition for request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkUpdateStatusForPossibleErrors(final UpdateInvitationUserInvitationStatusRequest request) {
        LOGGER.debug("Checking invitation user update status precondition for request - {}", request);
        if (!invitationUserService.existsByUuid(request.getUuid())) {
            LOGGER.error("Checking invitation user update status precondition for request - {} has been done with error, no invitation was found by uuid - {}", request, request.getUuid());
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.INVITATION_NOT_FOUND);
        }
        LOGGER.debug("Successfully checked invitation user update status precondition for request - {}", request);
        return SingleErrorWithStatus.empty();
    }
}