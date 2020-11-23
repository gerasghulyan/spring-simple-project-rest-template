package com.vntana.core.rest.facade.invitation.user.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
import com.vntana.core.domain.invitation.InvitationStatus;
import com.vntana.core.domain.invitation.user.InvitationOrganizationClientUser;
import com.vntana.core.domain.invitation.user.InvitationOrganizationUser;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.token.AbstractToken;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.invitation.InvitationStatusModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import com.vntana.core.model.invitation.user.request.*;
import com.vntana.core.model.invitation.user.response.*;
import com.vntana.core.model.invitation.user.response.model.AcceptInvitationUserResponseModel;
import com.vntana.core.model.invitation.user.response.model.GetAllByStatusUserInvitationsGridResponseModel;
import com.vntana.core.model.invitation.user.response.model.GetAllByStatusUserInvitationsResponseModel;
import com.vntana.core.model.invitation.user.response.model.GetByUserInvitationTokenResponseModel;
import com.vntana.core.rest.facade.invitation.user.InvitationUserServiceFacade;
import com.vntana.core.rest.facade.invitation.user.checker.InvitationUserFacadePreconditionChecker;
import com.vntana.core.rest.facade.invitation.user.component.InvitationUserSenderComponent;
import com.vntana.core.service.invitation.user.InvitationUserToClientService;
import com.vntana.core.service.invitation.user.InvitationUserToOrganizationService;
import com.vntana.core.service.invitation.user.dto.*;
import com.vntana.core.service.token.TokenService;
import com.vntana.core.service.token.invitation.user.TokenInvitationUserService;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.dto.CreateUserDto;
import com.vntana.core.service.user.role.UserRoleService;
import com.vntana.core.service.user.role.dto.UserGrantOrganizationRoleDto;
import ma.glasnost.orika.MapperFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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

    private final InvitationUserToOrganizationService invitationUserToOrganizationService;
    private final InvitationUserToClientService invitationUserToClientService;
    private final InvitationUserFacadePreconditionChecker preconditionChecker;
    private final TokenInvitationUserService tokenInvitationUserService;
    private final UserRoleService userRoleService;
    private final UserService userService;
    private final MapperFacade mapperFacade;
    private final TokenService tokenService;
    private final InvitationUserSenderComponent invitationUserSenderComponent;

    public InvitationUserServiceFacadeImpl(
            final InvitationUserToOrganizationService invitationUserToOrganizationService,
            final InvitationUserToClientService invitationUserToClientService,
            final InvitationUserFacadePreconditionChecker preconditionChecker,
            final TokenInvitationUserService tokenInvitationUserService,
            final UserRoleService userRoleService,
            final UserService userService,
            final MapperFacade mapperFacade,
            final TokenService tokenService,
            final InvitationUserSenderComponent invitationUserSenderComponent) {
        this.invitationUserToOrganizationService = invitationUserToOrganizationService;
        this.invitationUserToClientService = invitationUserToClientService;
        this.preconditionChecker = preconditionChecker;
        this.tokenInvitationUserService = tokenInvitationUserService;
        this.userRoleService = userRoleService;
        this.userService = userService;
        this.mapperFacade = mapperFacade;
        this.tokenService = tokenService;
        this.invitationUserSenderComponent = invitationUserSenderComponent;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Transactional
    @Override
    public CreateInvitationUserForOrganizationResultResponse createInvitationForOrganization(final CreateInvitationForOrganizationUserRequest request) {
        LOGGER.debug("Creating invitation user for request- {}", request);
        final SingleErrorWithStatus<InvitationUserErrorResponseModel> singleErrorWithStatus = preconditionChecker.checkCreateInvitationForOrganizationForPossibleErrors(request);
        if (singleErrorWithStatus.isPresent()) {
            return new CreateInvitationUserForOrganizationResultResponse(singleErrorWithStatus.getHttpStatus(), singleErrorWithStatus.getError());
        }
        final List<String> previouslyInvitedUuids = updatePreviouslyInvitedUserInvitationsStatuses(request.getEmail(), request.getOrganizationUuid());
        expirePreviouslyInvitedUserInvitationsTokens(previouslyInvitedUuids);
        final InvitationOrganizationUser invitationUser = invitationUserToOrganizationService.create(mapperFacade.map(request, CreateInvitationForOrganizationUserDto.class));
        LOGGER.debug("Successfully created invitation user for request- {}", request);
        return new CreateInvitationUserForOrganizationResultResponse(invitationUser.getUuid());
    }

    @Transactional
    @Override
    public CreateInvitationUserForOrganizationClientsResultResponse createInvitationForClient(final CreateInvitationForOrganizationClientUserRequest request) {
        LOGGER.debug("Creating invitation to client for request- {}", request);
        final SingleErrorWithStatus<InvitationUserErrorResponseModel> singleErrorWithStatus = preconditionChecker.checkCreateInvitationForClientsForPossibleErrors(request);
        if (singleErrorWithStatus.isPresent()) {
            return new CreateInvitationUserForOrganizationClientsResultResponse(singleErrorWithStatus.getHttpStatus(), singleErrorWithStatus.getError());
        }
        final List<InvitationOrganizationClientUser> invitationUsers = invitationUserToClientService.create(mapperFacade.map(request, CreateInvitationForClientsUserDto.class));
        LOGGER.debug("Successfully created invitation user for request- {}", request);
        return new CreateInvitationUserForOrganizationClientsResultResponse(collectInvitationUuids(invitationUsers));
    }

    @Override
    public GetAllByStatusUserInvitationsResultResponse getAllByOrganizationUuidAndStatus(final GetAllByStatusInvitationUserRequest request) {
        LOGGER.debug("Retrieving all user invitations by invitation status for request- {}", request);
        final SingleErrorWithStatus<InvitationUserErrorResponseModel> singleErrorWithStatus = preconditionChecker.checkGetAllByOrganizationUuidAndStatusForPossibleErrors(request);
        if (singleErrorWithStatus.isPresent()) {
            return new GetAllByStatusUserInvitationsResultResponse(singleErrorWithStatus.getHttpStatus(), singleErrorWithStatus.getError());
        }
        final Page<InvitationOrganizationUser> all = invitationUserToOrganizationService.getAllByOrganizationUuidAndStatus(mapperFacade.map(request, GetAllByOrganizationUuidAndStatusInvitationUsersDto.class));
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
        final InvitationOrganizationUser invitationUser = invitationUserToOrganizationService.updateStatus(mapperFacade.map(request, UpdateInvitationUserStatusDto.class));
        LOGGER.debug("Successfully invitation user invitation status for request- {}", request);
        return new UpdateInvitationUserInvitationStatusResultResponse(invitationUser.getUuid());
    }

    @Transactional
    @Override
    public SendInvitationUserResultResponse sendInvitationForOrganization(final SendInvitationForOrganizationUserRequest request) {
        LOGGER.debug("Processing invitation user facade sendInvitationForOrganization for request - {}", request);
        final SingleErrorWithStatus<InvitationUserErrorResponseModel> singleErrorWithStatus = preconditionChecker
                .checkSendInvitationForOrganizationForPossibleErrors(request);
        if (singleErrorWithStatus.isPresent()) {
            return new SendInvitationUserResultResponse(singleErrorWithStatus.getHttpStatus(), singleErrorWithStatus.getError());
        }
        final SendInvitationUserResultResponse resultResponse = invitationUserSenderComponent.sendInvitationForOrganization(request);
        LOGGER.debug("Successfully processed invitation user facade sendInvitationForOrganization for request - {}", request);
        return resultResponse;
    }

    @Override
    public SendInvitationUserResultResponse sendInvitationForClients(final SendInvitationForClientUserRequest request) {
        LOGGER.debug("Processing invitation user facade sendInvitationForClients for request - {}", request);
        final SingleErrorWithStatus<InvitationUserErrorResponseModel> singleErrorWithStatus = preconditionChecker
                .checkSendInvitationForClientsForPossibleErrors(request);
        if (singleErrorWithStatus.isPresent()) {
            return new SendInvitationUserResultResponse(singleErrorWithStatus.getHttpStatus(), singleErrorWithStatus.getError());
        }
        final SendInvitationUserResultResponse resultResponse = invitationUserSenderComponent.sendInvitationForClients(request);
        LOGGER.debug("Successfully processed invitation user facade sendInvitationForClients for request - {}", request);
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
        final InvitationOrganizationUser invitationUser = tokenInvitationUserService.getByToken(request.getToken()).getInvitationUser();
        final Organization organization = invitationUser.getOrganization();
        final User user = userService.getByEmail(invitationUser.getEmail());
        grantUserRoleFromInvitationAndMakeAccepted(invitationUser, user.getUuid(), request.getToken());
        LOGGER.debug("Successfully accepted invitation user for request- {}", request);
        return new AcceptInvitationUserResultResponse(
                new AcceptInvitationUserResponseModel(organization.getUuid(), user.getUuid(), UserRoleModel.valueOf(invitationUser.getRole().name()))
        );
    }

    @Transactional
    @Override
    public AcceptInvitationUserResultResponse acceptAndSignUp(final AcceptInvitationUserAndSignUpRequest request) {
        final SingleErrorWithStatus<InvitationUserErrorResponseModel> error = preconditionChecker.checkAcceptAndSignUpForPossibleErrors(request);
        if (error.isPresent()) {
            return new AcceptInvitationUserResultResponse(error.getHttpStatus(), error.getError());
        }
        final InvitationOrganizationUser invitationUser = tokenInvitationUserService.getByToken(request.getToken()).getInvitationUser();
        final User user = userService.create(new CreateUserDto(request.getNewUserFullName(), invitationUser.getEmail(), request.getPassword()));
        userService.makeVerified(invitationUser.getEmail());
        grantUserRoleFromInvitationAndMakeAccepted(invitationUser, user.getUuid(), request.getToken());
        return new AcceptInvitationUserResultResponse(
                new AcceptInvitationUserResponseModel(invitationUser.getOrganization().getUuid(),
                        user.getUuid(),
                        UserRoleModel.valueOf(invitationUser.getRole().name())
                )
        );
    }

    private void grantUserRoleFromInvitationAndMakeAccepted(final InvitationOrganizationUser invitationUser, final String userUuid, final String token) {
        final UserRole role = invitationUser.getRole();
        if (role == UserRole.ORGANIZATION_ADMIN) {
            userRoleService.grantOrganizationAdminRole(new UserGrantOrganizationRoleDto(userUuid, invitationUser.getOrganization().getUuid()));
            invitationUserToOrganizationService.updateStatus(new UpdateInvitationUserStatusDto(invitationUser.getUuid(), InvitationStatus.ACCEPTED));
            tokenService.findByTokenAndExpire(token);
        } else {
            throw new UnsupportedOperationException(String.format("Role %s is not supported yet to grant for user", role.name()));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public GetByUserInvitationTokenResultResponse getByToken(final String token) {
        LOGGER.debug("Retrieving invitation user by token");
        final SingleErrorWithStatus<InvitationUserErrorResponseModel> error = preconditionChecker.checkGetByTokenForPossibleErrors(token);
        if (error.isPresent()) {
            return new GetByUserInvitationTokenResultResponse(error.getHttpStatus(), error.getError());
        }
        final InvitationOrganizationUser invitationUser = invitationUserToOrganizationService.getByToken(token);
        final GetByUserInvitationTokenResultResponse resultResponse = new GetByUserInvitationTokenResultResponse(
                new GetByUserInvitationTokenResponseModel(
                        invitationUser.getUuid(),
                        invitationUser.getEmail(),
                        userService.existsByEmail(invitationUser.getEmail()),
                        invitationUser.getOrganization().getName(),
                        invitationUser.getInviterUser().getFullName(),
                        InvitationStatusModel.valueOf(invitationUser.getStatus().name())
                )
        );
        LOGGER.debug("Successfully retrieved the invitation user by token");
        return resultResponse;
    }

    private List<String> updatePreviouslyInvitedUserInvitationsStatuses(final String email, final String organizationUuid) {
        Assert.hasText(email, "The email should not be null or empty");
        Assert.hasText(organizationUuid, "The organizationUuid should not be null or empty");
        LOGGER.debug("Updating previously created user invitations for user having email - {} and for organization having uuid -{}", email, organizationUuid);
        final List<String> previouslyInvitedUuids = invitationUserToOrganizationService.getAllByEmailAndOrganizationUuidAndStatusOrderByCreatedDesc(new GetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(
                email,
                organizationUuid,
                InvitationStatus.INVITED)
        ).parallelStream()
                .map(invitationUser -> invitationUserToOrganizationService.updateStatus(new UpdateInvitationUserStatusDto(invitationUser.getUuid(), InvitationStatus.NOT_APPLICABLE)))
                .map(InvitationOrganizationUser::getUuid)
                .collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
        LOGGER.debug("Successfully updated previously created user invitations for user having email - {} and for organization having uuid -{}", email, organizationUuid);
        return previouslyInvitedUuids;
    }

    private void expirePreviouslyInvitedUserInvitationsTokens(final List<String> uuids) {
        Assert.notNull(uuids, "The previously  should not be null or empty");
        LOGGER.debug("Expiring previously created user invitations tokens for uuids - {}", uuids);
        uuids.parallelStream()
                .map(tokenInvitationUserService::findByInvitationUserUuid)
                .forEach(optional -> optional.ifPresent(AbstractToken::expire));
        LOGGER.debug("Successfully expired previously created user invitations tokens for uuids - {}", uuids);
    }

    private List<String> collectInvitationUuids(final List<InvitationOrganizationClientUser> invitationUsers) {
        return invitationUsers.stream().map(AbstractUuidAwareDomainEntity::getUuid).collect(Collectors.toList());
    }
}