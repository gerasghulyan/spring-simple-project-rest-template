package com.vntana.core.rest.facade.invitation.user.checker.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.invitation.user.InvitationOrganizationClientUser;
import com.vntana.core.domain.invitation.user.InvitationOrganizationUser;
import com.vntana.core.domain.token.TokenUserInvitationToOrganization;
import com.vntana.core.domain.token.TokenUserInvitationToOrganizationClient;
import com.vntana.core.domain.user.AbstractClientOrganizationAwareUserRole;
import com.vntana.core.domain.user.User;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import com.vntana.core.model.invitation.user.request.*;
import com.vntana.core.rest.facade.invitation.user.checker.InvitationUserFacadePreconditionChecker;
import com.vntana.core.rest.facade.invitation.user.component.UserRolesPermissionsCheckerComponent;
import com.vntana.core.service.client.OrganizationClientService;
import com.vntana.core.service.invitation.user.InvitationUserToClientService;
import com.vntana.core.service.invitation.user.InvitationUserToOrganizationService;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.token.invitation.user.TokenInvitationUserService;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.role.UserRoleService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 1:55 PM
 */
@Component
public class InvitationUserFacadePreconditionCheckerImpl implements InvitationUserFacadePreconditionChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvitationUserFacadePreconditionCheckerImpl.class);

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final OrganizationService organizationService;
    private final InvitationUserToOrganizationService invitationUserToOrganizationService;
    private final InvitationUserToClientService invitationUserToClientService;
    private final TokenInvitationUserService tokenInvitationUserService;
    private final OrganizationClientService clientOrganizationService;
    private final UserRolesPermissionsCheckerComponent userRolesPermissionsChecker;

    public InvitationUserFacadePreconditionCheckerImpl(
            final UserService userService,
            final UserRoleService userRoleService,
            final OrganizationService organizationService,
            final InvitationUserToOrganizationService invitationUserToOrganizationService,
            final InvitationUserToClientService invitationUserToClientService,
            final TokenInvitationUserService tokenInvitationUserService,
            final OrganizationClientService clientOrganizationService,
            final UserRolesPermissionsCheckerComponent userRolesPermissionsChecker) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.organizationService = organizationService;
        this.invitationUserToOrganizationService = invitationUserToOrganizationService;
        this.invitationUserToClientService = invitationUserToClientService;
        this.tokenInvitationUserService = tokenInvitationUserService;
        this.clientOrganizationService = clientOrganizationService;
        this.userRolesPermissionsChecker = userRolesPermissionsChecker;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkCreateInvitationForOrganizationForPossibleErrors(final CreateInvitationForOrganizationUserRequest request) {
        LOGGER.debug("Checking invitation user creation precondition for request - {}", request);
        if (UserRoleModel.ORGANIZATION_OWNER == request.getUserRole()) {
            LOGGER.debug("Checking invitation user creation for organization precondition for request - {} has been done with error, the invited role could not be organization owner", request);
            return SingleErrorWithStatus.of(HttpStatus.SC_CONFLICT, InvitationUserErrorResponseModel.INVITED_USER_ROLE_COULD_NOT_BE_ORGANIZATION_OWNER);
        }
        if (!userService.existsByUuid(request.getInviterUserUuid())) {
            LOGGER.debug("Checking invitation user creation for organization precondition for request - {} has been done with error, no inviter user was found by uuid - {}", request, request.getInviterUserUuid());
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND);
        }
        if (!organizationService.existsByUuid(request.getOrganizationUuid())) {
            LOGGER.debug("Checking invitation user creation for organization for organization  precondition for request - {} has been done with error, no organization was found by uuid - {}", request, request.getOrganizationUuid());
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.INVITING_ORGANIZATION_NOT_FOUND);
        }
        if (userService.findByEmailAndOrganizationUuid(request.getEmail(), request.getOrganizationUuid()).isPresent()) {
            LOGGER.debug("Checking invitation user creation for organization precondition for request - {} has been done with error, the invited user already is part of the organization", request);
            return SingleErrorWithStatus.of(HttpStatus.SC_CONFLICT, InvitationUserErrorResponseModel.USER_ALREADY_PART_OF_ORGANIZATION);
        }
        LOGGER.debug("Successfully checked invitation user creation for organization precondition for request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkCreateInvitationForClientsForPossibleErrors(final CreateInvitationForOrganizationClientUserRequest request) {
        LOGGER.debug("Checking invitation user for clients creation precondition for request - {}", request);
        if (!userService.existsByUuid(request.getInviterUserUuid())) {
            LOGGER.debug("Checking invitation user creation for organization precondition for request - {} has been done with error, no inviter user was found by uuid - {}", request, request.getInviterUserUuid());
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND);
        }
        if (!organizationService.existsByUuid(request.getOrganizationUuid())) {
            LOGGER.debug("Checking invitation user creation for organization client precondition for request - {} has been done with error, no organization was found by uuid - {}", request, request.getOrganizationUuid());
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.INVITING_ORGANIZATION_NOT_FOUND);
        }
        // Checking if invitations client uuids and user roles are valid
        final List<SingleUserInvitationToClientRequestModel> firstLevelFilteredInvitations = request.getInvitations().stream()
                .filter(invitation -> invitation.getRole().getPriority() >= UserRoleModel.CLIENT_ORGANIZATION_ADMIN.getPriority())
                .filter(invitation -> clientOrganizationService.existsByUuid(invitation.getClientUuid()))
                .collect(Collectors.toList());
        if (request.getInvitations().size() != firstLevelFilteredInvitations.size()) {
            return SingleErrorWithStatus.of(HttpStatus.SC_CONFLICT, InvitationUserErrorResponseModel.INCORRECT_PERMISSIONS);
        }
        // Checking if inviter user has super admin level permissions
        final User user = userService.getByUuid(request.getInviterUserUuid());
        if (user.roleOfSuperAdmin().isPresent()) {
            return SingleErrorWithStatus.empty();
        }
        // Checking if inviter user has organization level permissions
        final boolean isInviterOrganizationLevelUser = userRoleService.findByOrganizationAndUser(request.getOrganizationUuid(), request.getInviterUserUuid())
                .filter(role -> role.getUserRole().hasOrganizationAbility())
                .isPresent();
        if (isInviterOrganizationLevelUser) {
            return SingleErrorWithStatus.empty();
        }
        // Checking if inviter user has valid client level permissions to invite a new user 
        final Map<String, UserRoleModel> inviterClientLevelPermissionsMap =
                getClientOrganizationsByUserUuidAndOrganization(request.getOrganizationUuid(), request.getInviterUserUuid());
        final List<SingleUserInvitationToClientRequestModel> secondLevelFilteredInvitations = request.getInvitations().stream()
                .filter(invitation -> inviterClientLevelPermissionsMap.containsKey(invitation.getClientUuid()))
                .filter(invitation -> {
                    final UserRoleModel inviterRole = inviterClientLevelPermissionsMap.get(invitation.getClientUuid());
                    final UserRoleModel invitedRole = invitation.getRole();
                    return userRolesPermissionsChecker.isPermittedToInvite(inviterRole, invitedRole);
                })
                .collect(Collectors.toList());
        if (request.getInvitations().size() != secondLevelFilteredInvitations.size()) {
            return SingleErrorWithStatus.of(HttpStatus.SC_CONFLICT, InvitationUserErrorResponseModel.INCORRECT_PERMISSIONS);
        }
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkUpdateStatusForPossibleErrors(final UpdateInvitationUserInvitationStatusRequest request) {
        LOGGER.debug("Checking invitation user update status precondition for request - {}", request);
        if (!invitationUserToOrganizationService.existsByUuid(request.getUuid())) {
            LOGGER.debug("Checking invitation user update status precondition for request - {} has been done with error, no invitation was found by uuid - {}", request, request.getUuid());
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.INVITATION_NOT_FOUND);
        }
        LOGGER.debug("Successfully checked invitation user update status precondition for request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkAcceptForOrganizationForPossibleErrors(final AcceptInvitationUserRequest request) {
        LOGGER.debug("Checking invitation user for organization accept precondition for request - {}", request);
        final Optional<TokenUserInvitationToOrganization> tokenInvitationUserOptional = tokenInvitationUserService.findByOrganizationInvitationToken(request.getToken());
        if (!tokenInvitationUserOptional.isPresent()) {
            LOGGER.debug("Checking invitation user accept for organization precondition for request - {} has been done with error, token not found", request);
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN);
        }
        final InvitationOrganizationUser invitationUser = tokenInvitationUserOptional.get().getInvitationUser();
        final User user = userService.getByEmail(invitationUser.getEmail());
        if (userRoleService.findByOrganizationAndUser(invitationUser.getOrganization().getUuid(), user.getUuid()).isPresent()) {
            return SingleErrorWithStatus.of(HttpStatus.SC_CONFLICT, InvitationUserErrorResponseModel.USER_ALREADY_HAS_ROLE_IN_ORGANIZATION);
        }
        if (tokenInvitationUserOptional.get().isExpired()) {
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_ACCEPTABLE, InvitationUserErrorResponseModel.TOKEN_IS_EXPIRED);
        }
        LOGGER.debug("Successfully checked invitation user for organization accept precondition for request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkAcceptForClientForPossibleErrors(final AcceptInvitationUserRequest request) {
        LOGGER.debug("Checking invitation user for client accept precondition for request - {}", request);
        final Optional<TokenUserInvitationToOrganizationClient> userInvitationTokenOptional = tokenInvitationUserService.findByClientInvitationToken(request.getToken());
        if (!userInvitationTokenOptional.isPresent()) {
            LOGGER.debug("Checking invitation user accept for client precondition for request - {} has been done with error, token not found", request);
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN);
        }
        final InvitationOrganizationClientUser userInvitation = userInvitationTokenOptional.get().getUserInvitation();
        final User user = userService.getByEmail(userInvitation.getEmail());
        if (userRoleService.findByClientOrganizationAndUser(userInvitation.getClientOrganization().getUuid(), user.getUuid()).isPresent()) {
            LOGGER.debug("Checking invitation user accept for client precondition for request - {} has been done with error, user already has role in client", request);
            return SingleErrorWithStatus.of(HttpStatus.SC_CONFLICT, InvitationUserErrorResponseModel.USER_ALREADY_HAS_ROLE_IN_CLIENT);
        }
        if (userInvitationTokenOptional.get().isExpired()) {
            LOGGER.debug("Checking invitation user accept for client precondition for request - {} has been done with error, token is expired", request);
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_ACCEPTABLE, InvitationUserErrorResponseModel.TOKEN_IS_EXPIRED);
        }
        LOGGER.debug("Successfully checked invitation user for client accept precondition for request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkAcceptAndSignInvitationToOrganizationUpForPossibleErrors(final AcceptInvitationUserAndSignUpRequest request) {
        final Optional<TokenUserInvitationToOrganization> tokenInvitationUserOptional = tokenInvitationUserService.findByOrganizationInvitationToken(request.getToken());
        if (!tokenInvitationUserOptional.isPresent()) {
            LOGGER.debug("Checking invitation user accept precondition for request - {} has been done with error, token not found", request);
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN);
        }
        final TokenUserInvitationToOrganization tokenInvitationUser = tokenInvitationUserOptional.get();
        if (tokenInvitationUser.isExpired()) {
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_ACCEPTABLE, InvitationUserErrorResponseModel.TOKEN_IS_EXPIRED);
        }
        if (userService.existsByEmail(tokenInvitationUser.getInvitationUser().getEmail())) {
            return SingleErrorWithStatus.of(HttpStatus.SC_CONFLICT, InvitationUserErrorResponseModel.USER_ALREADY_EXISTS);
        }
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkAcceptAndSignInvitationToClientUpForPossibleErrors(final AcceptInvitationUserAndSignUpRequest request) {
        final Optional<TokenUserInvitationToOrganizationClient> userInvitationTokenOptional = tokenInvitationUserService.findByClientInvitationToken(request.getToken());
        if (!userInvitationTokenOptional.isPresent()) {
            LOGGER.debug("Checking invitation user accept for client precondition for request - {} has been done with error, token not found", request);
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN);
        }
        final TokenUserInvitationToOrganizationClient userInvitationToken = userInvitationTokenOptional.get();
        if (userInvitationToken.isExpired()) {
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_ACCEPTABLE, InvitationUserErrorResponseModel.TOKEN_IS_EXPIRED);
        }
        if (userService.existsByEmail(userInvitationToken.getUserInvitation().getEmail())) {
            return SingleErrorWithStatus.of(HttpStatus.SC_CONFLICT, InvitationUserErrorResponseModel.USER_ALREADY_EXISTS);
        }
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkSendInvitationForOrganizationForPossibleErrors(final SendInvitationForOrganizationUserRequest request) {
        LOGGER.debug("Checking invitation user send invitation for organization precondition for request - {}", request);
        return checkSendInvitationForUserAndOrganizationExistence(request.getOrganizationUuid(), request.getInviterUserUuid());
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkSendInvitationForClientsForPossibleErrors(final SendInvitationForClientUserRequest request) {
        LOGGER.debug("Checking invitation user send invitation for clients precondition for request - {}", request);
        return checkSendInvitationForUserAndOrganizationExistence(request.getOrganizationUuid(), request.getInviterUserUuid());
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkGetByTokenInvitationToOrganizationForPossibleErrors(final String token) {
        LOGGER.debug("Checking invitation user to organization get by token precondition");
        if (StringUtils.isEmpty(token)) {
            LOGGER.debug("Checking invitation user get by token precondition has been done with error, user invitation token is missing");
            return SingleErrorWithStatus.of(HttpStatus.SC_UNPROCESSABLE_ENTITY, InvitationUserErrorResponseModel.MISSING_INVITATION_TOKEN);
        }
        if (!tokenInvitationUserService.doesInvitationToOrganizationExist(token)) {
            LOGGER.debug("Checking invitation user get by token precondition has been done with error, user invitation token is not found");
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN);
        }
        if (tokenInvitationUserService.isInvitationToOrganizationExpired(token)) {
            LOGGER.debug("Checking invitation user get by token precondition has been done with error, user invitation token has been expired");
            return SingleErrorWithStatus.of(HttpStatus.SC_BAD_REQUEST, InvitationUserErrorResponseModel.INVALID_INVITATION_TOKEN);
        }
        if (!invitationUserToOrganizationService.existsByToken(token)) {
            LOGGER.debug("Checking invitation user get by token precondition has been done with error, no invitation user found by token");
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.INVITATION_NOT_FOUND);
        }
        LOGGER.debug("Successfully checked invitation user to organization get by token precondition");
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkGetByTokenInvitationToClientForPossibleErrors(final String token) {
        LOGGER.debug("Checking invitation user to client get by token precondition");
        if (StringUtils.isEmpty(token)) {
            LOGGER.debug("Checking invitation user get by token precondition has been done with error, user invitation token is missing");
            return SingleErrorWithStatus.of(HttpStatus.SC_UNPROCESSABLE_ENTITY, InvitationUserErrorResponseModel.MISSING_INVITATION_TOKEN);
        }
        if (!tokenInvitationUserService.doesInvitationToClientExist(token)) {
            LOGGER.debug("Checking invitation user get by token precondition has been done with error, user invitation token is not found");
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN);
        }
        if (tokenInvitationUserService.isInvitationToClientExpired(token)) {
            LOGGER.debug("Checking invitation user get by token precondition has been done with error, user invitation token has been expired");
            return SingleErrorWithStatus.of(HttpStatus.SC_BAD_REQUEST, InvitationUserErrorResponseModel.INVALID_INVITATION_TOKEN);
        }
        if (!invitationUserToClientService.existsByToken(token)) {
            LOGGER.debug("Checking invitation user get by token precondition has been done with error, no invitation user found by token");
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.INVITATION_NOT_FOUND);
        }
        LOGGER.debug("Successfully checked invitation user to client get by token precondition");
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkGetAllByOrganizationUuidAndStatusForPossibleErrors(final GetAllByStatusInvitationUserRequest request) {
        LOGGER.debug("Checking invitation user get all by organization uuid and status precondition for request - {}", request);
        if (!organizationService.existsByUuid(request.getOrganizationUuid())) {
            LOGGER.debug("Successfully checked invitation user get all by organization uuid and status precondition for request - {} has been done with error, no organization was found by uuid - {}", request, request.getOrganizationUuid());
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.INVITING_ORGANIZATION_NOT_FOUND);
        }
        LOGGER.debug("Successfully checked invitation user get all by organization uuid and status precondition for request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    private Map<String, UserRoleModel> getClientOrganizationsByUserUuidAndOrganization(final String organizationUuid, final String inviterUserUuid) {
        final List<AbstractClientOrganizationAwareUserRole> permittedClients = userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(organizationUuid, inviterUserUuid);
        return permittedClients.stream()
                .peek(permittedClient -> {
                    Assert.state(permittedClient.getClientOrganization() != null, "clientOrganization cannot be null.");
                    Assert.state(permittedClient.getUserRole() != null, "userRole cannot be null.");
                })
                .collect(toMap(
                        permittedClient -> permittedClient.getClientOrganization().getUuid(),
                        permittedClient -> UserRoleModel.valueOf(permittedClient.getUserRole().name())
                ));
    }

    private SingleErrorWithStatus<InvitationUserErrorResponseModel> checkSendInvitationForUserAndOrganizationExistence(final String organizationUuid, final String inviterUuid) {
        if (!userService.existsByUuid(inviterUuid)) {
            LOGGER.debug("Checking invitation user send invitation precondition has been done with error, no inviter user was found by uuid - {}", inviterUuid);
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND);
        }
        if (!organizationService.existsByUuid(organizationUuid)) {
            LOGGER.debug("Checking invitation user send invitation precondition has been done with error, no organization was found by uuid - {}", organizationUuid);
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.INVITING_ORGANIZATION_NOT_FOUND);
        }
        LOGGER.debug("Successfully checked invitation user send invitation precondition for inviter user uuid - {}, organization uuid - {}", inviterUuid, organizationUuid);
        return SingleErrorWithStatus.empty();
    }
}