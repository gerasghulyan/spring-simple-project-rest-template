package com.vntana.core.rest.facade.invitation.user.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.invitation.InvitationStatus;
import com.vntana.core.domain.invitation.user.InvitationUser;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import com.vntana.core.model.invitation.user.request.*;
import com.vntana.core.model.invitation.user.response.*;
import com.vntana.core.model.invitation.user.response.model.AcceptInvitationUserResponseModel;
import com.vntana.core.model.invitation.user.response.model.GetAllByStatusUserInvitationsGridResponseModel;
import com.vntana.core.model.invitation.user.response.model.GetAllByStatusUserInvitationsResponseModel;
import com.vntana.core.rest.facade.invitation.user.InvitationUserServiceFacade;
import com.vntana.core.rest.facade.invitation.user.checker.InvitationUserFacadePreconditionChecker;
import com.vntana.core.rest.facade.invitation.user.component.InvitationUserSenderComponent;
import com.vntana.core.service.invitation.user.InvitationUserService;
import com.vntana.core.service.invitation.user.dto.CreateInvitationUserDto;
import com.vntana.core.service.invitation.user.dto.GetAllByStatusInvitationUsersDto;
import com.vntana.core.service.invitation.user.dto.GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto;
import com.vntana.core.service.invitation.user.dto.UpdateInvitationUserStatusDto;
import com.vntana.core.service.token.invitation.user.TokenInvitationUserService;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.role.UserRoleService;
import com.vntana.core.service.user.role.dto.UserGrantOrganizationRoleDto;
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
    private final TokenInvitationUserService tokenInvitationUserService;
    private final UserRoleService userRoleService;
    private final UserService userService;
    private final MapperFacade mapperFacade;
    private final InvitationUserSenderComponent invitationUserSenderComponent;

    public InvitationUserServiceFacadeImpl(final InvitationUserService invitationUserService,
                                           final InvitationUserFacadePreconditionChecker preconditionChecker,
                                           final TokenInvitationUserService tokenInvitationUserService,
                                           final UserRoleService userRoleService,
                                           final UserService userService,
                                           final MapperFacade mapperFacade,
                                           final InvitationUserSenderComponent invitationUserSenderComponent) {
        this.invitationUserService = invitationUserService;
        this.preconditionChecker = preconditionChecker;
        this.tokenInvitationUserService = tokenInvitationUserService;
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.mapperFacade = mapperFacade;
        this.invitationUserSenderComponent = invitationUserSenderComponent;
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

    @Transactional
    @Override
    public SendInvitationUserResultResponse sendInvitation(final SendInvitationUserRequest request) {
        LOGGER.debug("Processing invitation user facade sendInvitation for request - {}", request);
        final SingleErrorWithStatus<InvitationUserErrorResponseModel> singleErrorWithStatus = preconditionChecker.checkSendInvitationForPossibleErrors(request);
        if (singleErrorWithStatus.isPresent()) {
            return new SendInvitationUserResultResponse(singleErrorWithStatus.getHttpStatus(), singleErrorWithStatus.getError());
        }
        final SendInvitationUserResultResponse resultResponse = invitationUserSenderComponent.sendInvitation(request);
        LOGGER.debug("Successfully processed invitation user facade sendInvitation for request - {}", request);
        return resultResponse;
    }

    @Transactional
    @Override
    public AcceptInvitationUserResultResponse accept(final AcceptInvitationUserRequest request) {
        LOGGER.debug("Accepting invitation user for request- {}", request);
        final SingleErrorWithStatus<InvitationUserErrorResponseModel> error = preconditionChecker.checkAcceptForPossibleErrors(request);
        if (error.isPresent()) {
            return new AcceptInvitationUserResultResponse(error.getHttpStatus(), error.getError());
        }
        final InvitationUser invitationUser = tokenInvitationUserService.getByToken(request.getToken()).getInvitationUser();
        final Organization organization = invitationUser.getOrganization();
        final User user = userService.getByEmail(invitationUser.getEmail());
        final UserRole role = invitationUser.getRole();
        if (role == UserRole.ORGANIZATION_ADMIN) {
            userRoleService.grantOrganizationAdminRole(new UserGrantOrganizationRoleDto(user.getUuid(), organization.getUuid()));
        } else {
            throw new UnsupportedOperationException(String.format("Role %s is not supported yet to grant for user", role.name()));
        }
        LOGGER.debug("Successfully accepted invitation user for request- {}", request);
        return new AcceptInvitationUserResultResponse(new AcceptInvitationUserResponseModel(organization.getUuid(), user.getUuid(), UserRoleModel.valueOf(role.name())));
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