package com.vntana.core.rest.facade.invitation.user.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.invitation.InvitationStatus;
import com.vntana.core.domain.invitation.user.InvitationUser;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import com.vntana.core.model.invitation.user.request.CreateInvitationUserRequest;
import com.vntana.core.model.invitation.user.response.CreateInvitationUserResponse;
import com.vntana.core.rest.facade.invitation.user.InvitationUserServiceFacade;
import com.vntana.core.rest.facade.invitation.user.checker.InvitationUserFacadePreconditionChecker;
import com.vntana.core.service.invitation.user.InvitationUserService;
import com.vntana.core.service.invitation.user.dto.CreateInvitationUserDto;
import com.vntana.core.service.invitation.user.dto.GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto;
import com.vntana.core.service.invitation.user.dto.UpdateInvitationUserStatusDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 1:53 PM
 */
@Component
public class InvitationUserServiceFacadeImpl implements InvitationUserServiceFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationUserServiceFacadeImpl.class);

    private final InvitationUserService invitationUserService;
    private final InvitationUserFacadePreconditionChecker preconditionChecker;

    public InvitationUserServiceFacadeImpl(final InvitationUserService invitationUserService,
                                           final InvitationUserFacadePreconditionChecker preconditionChecker) {
        this.invitationUserService = invitationUserService;
        this.preconditionChecker = preconditionChecker;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public CreateInvitationUserResponse create(final CreateInvitationUserRequest request) {
        LOGGER.debug("Creating invitation user for request- {}", request);
        final SingleErrorWithStatus<InvitationUserErrorResponseModel> singleErrorWithStatus = preconditionChecker.checkCreateForPossibleErrors(request);
        if (singleErrorWithStatus.isPresent()) {
            return new CreateInvitationUserResponse(singleErrorWithStatus.getHttpStatus(), singleErrorWithStatus.getError());
        }
        updatePreviouslyInvitedUserInvitationsStatuses(request.getEmail(), request.getOrganizationUuid());
        final InvitationUser invitationUser = invitationUserService.create(new CreateInvitationUserDto(
                UserRole.valueOf(request.getUserRole().name()),
                request.getEmail(),
                request.getInviterUserUuid(),
                request.getOrganizationUuid()
        ));
        LOGGER.debug("Successfully created invitation user for request- {}", request);
        return new CreateInvitationUserResponse(invitationUser.getUuid());
    }

    private void updatePreviouslyInvitedUserInvitationsStatuses(final String email, final String organizationUuid) {
        LOGGER.debug("Updating previously created user invitations for user having email - {} and for organization having uuid -{}", email, organizationUuid);
        invitationUserService.getByEmailAndOrganizationUuidAndStatusOrderByCreatedDesc(new GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(
                email,
                organizationUuid,
                InvitationStatus.INVITED)
        ).parallelStream().forEach(invitationUser -> invitationUserService.updateStatus(new UpdateInvitationUserStatusDto(invitationUser.getUuid(), InvitationStatus.NOT_APPLICABLE)));
        LOGGER.debug("Successfully updated previously created user invitations for user having email - {} and for organization having uuid -{}", email, organizationUuid);
    }
}