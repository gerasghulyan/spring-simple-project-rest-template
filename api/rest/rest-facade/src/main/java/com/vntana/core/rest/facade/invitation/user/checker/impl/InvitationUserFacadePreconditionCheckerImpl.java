package com.vntana.core.rest.facade.invitation.user.checker.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.commons.persistence.domain.AbstractUuidAwareDomainEntity;
import com.vntana.core.domain.invitation.user.InvitationOrganizationUser;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.token.TokenInvitationUser;
import com.vntana.core.domain.user.User;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel;
import com.vntana.core.model.invitation.user.request.*;
import com.vntana.core.rest.facade.invitation.user.checker.InvitationUserFacadePreconditionChecker;
import com.vntana.core.rest.facade.invitation.user.checker.UserRolesComparator;
import com.vntana.core.service.client.ClientOrganizationService;
import com.vntana.core.service.invitation.user.InvitationUserService;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.token.invitation.user.TokenInvitationUserService;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.role.UserRoleService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final InvitationUserService invitationUserService;
    private final TokenInvitationUserService tokenInvitationUserService;
    private final ClientOrganizationService clientOrganizationService;
    private final UserRolesComparator userRolesComparator;

    public InvitationUserFacadePreconditionCheckerImpl(final UserService userService,
                                                       final UserRoleService userRoleService,
                                                       final OrganizationService organizationService,
                                                       final InvitationUserService invitationUserService,
                                                       final TokenInvitationUserService tokenInvitationUserService,
                                                       final ClientOrganizationService clientOrganizationService,
                                                       final UserRolesComparator userRolesComparator) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.organizationService = organizationService;
        this.invitationUserService = invitationUserService;
        this.tokenInvitationUserService = tokenInvitationUserService;
        this.clientOrganizationService = clientOrganizationService;
        this.userRolesComparator = userRolesComparator;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkCreateForPossibleErrors(final CreateInvitationForOrganizationUserRequest request) {
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

        final List<String> clientsUuids = extractClientOrganizations(request.getOrganizationUuid());

        request.getUserRoles().entrySet().stream().map(entry -> {
            if (entry.getValue().getPriority() < UserRoleModel.CLIENT_ADMIN.getPriority()) {
                LOGGER.debug("Checking invitation user creation for organization client precondition for request - {} has been done with error, the invited role could not be organization owner", request);
                return SingleErrorWithStatus.of(HttpStatus.SC_CONFLICT, InvitationUserErrorResponseModel.WRONG_PERMISSIONS);
            }
            if (!clientOrganizationService.existsByUuid(entry.getKey())) {
                LOGGER.debug("Checking invitation user creation for organization client precondition for request - {} has been done with error, no organization was found by uuid - {}", request, entry.getKey());
                return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.INVITING_CLIENT_NOT_FOUND);
            }
            if (!clientsUuids.contains(entry.getKey())) {
                LOGGER.debug("Checking invitation user creation for organization client precondition for request - {} has been done with error, organization don't contain client - {}", request, entry.getKey());
                return SingleErrorWithStatus.of(HttpStatus.SC_NOT_ACCEPTABLE, InvitationUserErrorResponseModel.CLIENT_NOT_MATCHING_ORGANIZATION);
            }
            return SingleErrorWithStatus.empty();
        });
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkUpdateStatusForPossibleErrors(final UpdateInvitationUserInvitationStatusRequest request) {
        LOGGER.debug("Checking invitation user update status precondition for request - {}", request);
        if (!invitationUserService.existsByUuid(request.getUuid())) {
            LOGGER.debug("Checking invitation user update status precondition for request - {} has been done with error, no invitation was found by uuid - {}", request, request.getUuid());
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.INVITATION_NOT_FOUND);
        }
        LOGGER.debug("Successfully checked invitation user update status precondition for request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkAcceptForPossibleErrors(final AcceptInvitationUserRequest request) {
        LOGGER.debug("Checking invitation user accept precondition for request - {}", request);
        final Optional<TokenInvitationUser> tokenInvitationUserOptional = tokenInvitationUserService.findByToken(request.getToken());
        if (!tokenInvitationUserOptional.isPresent()) {
            LOGGER.debug("Checking invitation user accept precondition for request - {} has been done with error, token not found", request);
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
        LOGGER.debug("Successfully checked invitation user accept precondition for request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkAcceptAndSignUpForPossibleErrors(final AcceptInvitationUserAndSignUpRequest request) {
        final Optional<TokenInvitationUser> tokenInvitationUserOptional = tokenInvitationUserService.findByToken(request.getToken());
        if (!tokenInvitationUserOptional.isPresent()) {
            LOGGER.debug("Checking invitation user accept precondition for request - {} has been done with error, token not found", request);
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN);
        }
        final TokenInvitationUser tokenInvitationUser = tokenInvitationUserOptional.get();
        if (tokenInvitationUser.isExpired()) {
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_ACCEPTABLE, InvitationUserErrorResponseModel.TOKEN_IS_EXPIRED);
        }
        if (userService.existsByEmail(tokenInvitationUser.getInvitationUser().getEmail())) {
            return SingleErrorWithStatus.of(HttpStatus.SC_CONFLICT, InvitationUserErrorResponseModel.USER_ALREADY_EXISTS);
        }
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkSendInvitationForPossibleErrors(final SendInvitationUserRequest request) {
        LOGGER.debug("Checking invitation user send invitation precondition for request - {}", request);
        if (!userService.existsByUuid(request.getInviterUserUuid())) {
            LOGGER.debug("Checking invitation user send invitation precondition for request - {} has been done with error, no inviter user was found by uuid - {}", request, request.getInviterUserUuid());
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND);
        }
        if (!organizationService.existsByUuid(request.getOrganizationUuid())) {
            LOGGER.debug("Checking invitation user send invitation precondition for request - {} has been done with error, no organization was found by uuid - {}", request, request.getOrganizationUuid());
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.INVITING_ORGANIZATION_NOT_FOUND);
        }
        LOGGER.debug("Successfully checked invitation user send invitation precondition for request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<InvitationUserErrorResponseModel> checkGetByTokenForPossibleErrors(final String token) {
        LOGGER.debug("Checking invitation user get by token precondition");
        if (StringUtils.isEmpty(token)) {
            LOGGER.debug("Checking invitation user get by token precondition has been done with error, user invitation token is missing");
            return SingleErrorWithStatus.of(HttpStatus.SC_UNPROCESSABLE_ENTITY, InvitationUserErrorResponseModel.MISSING_INVITATION_TOKEN);
        }
        if (!tokenInvitationUserService.isExists(token)) {
            LOGGER.debug("Checking invitation user get by token precondition has been done with error, user invitation token is not found");
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN);
        }
        if (tokenInvitationUserService.isExpired(token)) {
            LOGGER.debug("Checking invitation user get by token precondition has been done with error, user invitation token has been expired");
            return SingleErrorWithStatus.of(HttpStatus.SC_BAD_REQUEST, InvitationUserErrorResponseModel.INVALID_INVITATION_TOKEN);
        }
        if (!invitationUserService.existsByToken(token)) {
            LOGGER.debug("Checking invitation user get by token precondition has been done with error, no invitation user found by token");
            return SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, InvitationUserErrorResponseModel.INVITATION_NOT_FOUND);
        }
        LOGGER.debug("Successfully checked invitation user get by token precondition");
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

    private List<String> extractClientOrganizations(String organizationUuid) {
        final Organization organization = organizationService
                .findByUuid(organizationUuid)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Cannot find organization for uuid - %s", organizationUuid)));
        return organization.getClientOrganizations().stream().map(AbstractUuidAwareDomainEntity::getUuid).collect(Collectors.toList());
    }
}