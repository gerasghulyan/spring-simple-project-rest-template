package com.vntana.core.rest.facade.invitation.user.checker.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import com.vntana.core.model.invitation.user.request.CreateInvitationUserRequest;
import com.vntana.core.rest.facade.invitation.user.checker.InvitationUserFacadePreconditionChecker;
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

    public InvitationUserFacadePreconditionCheckerImpl(final UserService userService, final OrganizationService organizationService) {
        this.userService = userService;
        this.organizationService = organizationService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkCreateForPossibleErrors(final CreateInvitationUserRequest request) {
        LOGGER.debug("Checking invitation user creation precondition for request - {}", request);
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
}