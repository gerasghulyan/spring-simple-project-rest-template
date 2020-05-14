package com.vntana.core.rest.facade.invitation.user.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.invitation.InvitationStatus;
import com.vntana.core.domain.invitation.user.InvitationUser;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import com.vntana.core.model.invitation.user.request.CreateInvitationUserRequest;
import com.vntana.core.model.invitation.user.request.GetAllByStatusInvitationUserRequest;
import com.vntana.core.model.invitation.user.request.UpdateInvitationUserInvitationStatusRequest;
import com.vntana.core.model.invitation.user.response.CreateInvitationUserResultResponse;
import com.vntana.core.model.invitation.user.response.GetAllByStatusUserInvitationsResultResponse;
import com.vntana.core.model.invitation.user.response.UpdateInvitationUserInvitationStatusResultResponse;
import com.vntana.core.model.invitation.user.response.model.GetAllByStatusUserInvitationsGridResponseModel;
import com.vntana.core.model.invitation.user.response.model.GetAllByStatusUserInvitationsResponseModel;
import com.vntana.core.rest.facade.invitation.user.InvitationUserServiceFacade;
import com.vntana.core.rest.facade.invitation.user.checker.InvitationUserFacadePreconditionChecker;
import com.vntana.core.service.invitation.user.InvitationUserService;
import com.vntana.core.service.invitation.user.dto.CreateInvitationUserDto;
import com.vntana.core.service.invitation.user.dto.GetAllByStatusInvitationUsersDto;
import com.vntana.core.service.invitation.user.dto.GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto;
import com.vntana.core.service.invitation.user.dto.UpdateInvitationUserStatusDto;
import ma.glasnost.orika.MapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    private final MapperFacade mapperFacade;

    public InvitationUserServiceFacadeImpl(final InvitationUserService invitationUserService,
                                           final InvitationUserFacadePreconditionChecker preconditionChecker,
                                           final MapperFacade mapperFacade) {
        this.invitationUserService = invitationUserService;
        this.preconditionChecker = preconditionChecker;
        this.mapperFacade = mapperFacade;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Transactional
    @Override
    public CreateInvitationUserResultResponse create(final CreateInvitationUserRequest request) {
        LOGGER.debug("Creating invitation user for request- {}", request);
        final SingleErrorWithStatus<InvitationUserErrorResponseModel> singleErrorWithStatus = preconditionChecker.checkCreateForPossibleErrors(request);
        if (singleErrorWithStatus.isPresent()) {
            return new CreateInvitationUserResultResponse(singleErrorWithStatus.getHttpStatus(), singleErrorWithStatus.getError());
        }
        updatePreviouslyInvitedUserInvitationsStatuses(request.getEmail(), request.getOrganizationUuid());
        final InvitationUser invitationUser = invitationUserService.create(mapperFacade.map(request, CreateInvitationUserDto.class));
        LOGGER.debug("Successfully created invitation user for request- {}", request);
        return new CreateInvitationUserResultResponse(invitationUser.getUuid());
    }

    @Override
    public GetAllByStatusUserInvitationsResultResponse getAllByStatus(final GetAllByStatusInvitationUserRequest request) {
        LOGGER.debug("Retrieving all user invitations by invitation status for request- {}", request);
        final Page<InvitationUser> all = invitationUserService.getAllByStatus(mapperFacade.map(request, GetAllByStatusInvitationUsersDto.class));
        final List<GetAllByStatusUserInvitationsResponseModel> responseModels = all.stream()
                .map(invitationUser -> new GetAllByStatusUserInvitationsResponseModel(
                        invitationUser.getUuid(),
                        UserRoleModel.valueOf(invitationUser.getRole().name()),
                        invitationUser.getEmail())
                )
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        final GetAllByStatusUserInvitationsGridResponseModel gridResponseModel = new GetAllByStatusUserInvitationsGridResponseModel(responseModels.size(), responseModels);
        LOGGER.debug("Successfully retrieved all user invitations by invitation status for request- {}", request);
        return new GetAllByStatusUserInvitationsResultResponse(gridResponseModel);
    }

    @Transactional
    @Override
    public UpdateInvitationUserInvitationStatusResultResponse updateStatus(final UpdateInvitationUserInvitationStatusRequest request) {
        LOGGER.debug("Updating invitation user invitation status for request- {}", request);
        final SingleErrorWithStatus<InvitationUserErrorResponseModel> singleErrorWithStatus = preconditionChecker.checkUpdateStatusForPossibleErrors(request);
        if (singleErrorWithStatus.isPresent()) {
            return new UpdateInvitationUserInvitationStatusResultResponse(singleErrorWithStatus.getHttpStatus(), singleErrorWithStatus.getError());
        }
        final InvitationUser invitationUser = invitationUserService.updateStatus(mapperFacade.map(request, UpdateInvitationUserStatusDto.class));
        LOGGER.debug("Successfully invitation user invitation status for request- {}", request);
        return new UpdateInvitationUserInvitationStatusResultResponse(invitationUser.getUuid());
    }

    private void updatePreviouslyInvitedUserInvitationsStatuses(final String email, final String organizationUuid) {
        LOGGER.debug("Updating previously created user invitations for user having email - {} and for organization having uuid -{}", email, organizationUuid);
        invitationUserService.getAllByEmailAndOrganizationUuidAndStatusOrderByCreatedDesc(new GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(
                email,
                organizationUuid,
                InvitationStatus.INVITED)
        ).parallelStream().forEach(invitationUser -> invitationUserService.updateStatus(new UpdateInvitationUserStatusDto(invitationUser.getUuid(), InvitationStatus.NOT_APPLICABLE)));
        LOGGER.debug("Successfully updated previously created user invitations for user having email - {} and for organization having uuid -{}", email, organizationUuid);
    }
}