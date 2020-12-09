package com.vntana.core.rest.facade.user.role.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.user.*;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import com.vntana.core.model.user.role.request.*;
import com.vntana.core.model.user.role.response.*;
import com.vntana.core.rest.facade.user.role.UserRoleServiceFacade;
import com.vntana.core.rest.facade.user.role.component.UserRoleFacadePreconditionCheckerComponent;
import com.vntana.core.service.client.OrganizationClientService;
import com.vntana.core.service.token.auth.TokenAuthenticationService;
import com.vntana.core.service.user.role.UserRoleService;
import com.vntana.core.service.user.role.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 11:31 AM
 */
@Component
public class UserRoleServiceFacadeImpl implements UserRoleServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceFacadeImpl.class);

    private final UserRoleFacadePreconditionCheckerComponent preconditionChecker;
    private final TokenAuthenticationService tokenAuthenticationService;
    private final UserRoleService userRoleService;
    private final OrganizationClientService organizationClientService;

    public UserRoleServiceFacadeImpl(final UserRoleFacadePreconditionCheckerComponent preconditionChecker,
                                     final TokenAuthenticationService tokenAuthenticationService,
                                     final UserRoleService userRoleService,
                                     final OrganizationClientService organizationClientService
    ) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.preconditionChecker = preconditionChecker;
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.organizationClientService = organizationClientService;
        this.userRoleService = userRoleService;
    }

    @Transactional
    @Override
    public UserRoleGrantSuperAdminResponse grantSuperAdmin(final UserRoleGrantSuperAdminRequest request) {
        LOGGER.debug("Granting user super admin role for request - {}", request);
        final SingleErrorWithStatus<UserRoleErrorResponseModel> error = preconditionChecker.checkGrantSuperAdmin(request);
        if (error.isPresent()) {
            return new UserRoleGrantSuperAdminResponse(error.getHttpStatus(), error.getError());
        }
        userRoleService.grantSuperAdminRole(request.getUserUuid());
        LOGGER.debug("Successfully granted user super admin role for request - {}", request);
        return new UserRoleGrantSuperAdminResponse(request.getUserUuid());
    }

    @Transactional
    @Override
    public UserRoleGrantOrganizationAdminResponse grantOrganizationAdminRole(final UserRoleGrantOrganizationAdminRequest request) {
        LOGGER.debug("Granting user organization role for request - {}", request);
        final SingleErrorWithStatus<UserRoleErrorResponseModel> error = preconditionChecker.checkGrantOrganizationAdminRole(request);
        if (error.isPresent()) {
            return new UserRoleGrantOrganizationAdminResponse(error.getHttpStatus(), error.getError());
        }
        revokeUserOrganizationClients(request.getOrganizationUuid(), request.getUserUuid());
        final UserOrganizationAdminRole adminRole = userRoleService.grantOrganizationAdminRole(new UserGrantOrganizationRoleDto(request.getUserUuid(), request.getOrganizationUuid()));
        LOGGER.debug("Successfully granted user organization role for request - {}", request);
        return new UserRoleGrantOrganizationAdminResponse(adminRole.getUser().getUuid());
    }

    @Transactional
    @Override
    public UserRoleGrantClientOrganizationResponse grantClientRole(final UserRoleGrantClientOrganizationRequest request) {
        LOGGER.debug("Granting user client role for request - {}", request);
        final SingleErrorWithStatus<UserRoleErrorResponseModel> error = preconditionChecker.checkGrantClientRole(request);
        if (error.isPresent()) {
            return new UserRoleGrantClientOrganizationResponse(error.getHttpStatus(), error.getError());
        }
        final String organizationUuid = organizationClientService.getByUuid(request.getClientUuid()).getOrganization().getUuid();
        revokeIfUserOrganizationRolePresent(organizationUuid, request.getUserUuid());
        final AbstractClientOrganizationAwareUserRole clientRole = userRoleService.grantClientRole(new UserGrantClientRoleDto(
                request.getUserUuid(),
                request.getClientUuid(),
                UserRole.valueOf(request.getUserRole().name())
        ));
        LOGGER.debug("Successfully granted user organization client role for request - {}", request);
        return new UserRoleGrantClientOrganizationResponse(clientRole.getUser().getUuid());
    }

    @Transactional
    @Override
    public UserRoleRevokeOrganizationAdminResponse revokeOrganizationAdminRole(final UserRoleRevokeOrganizationAdminRequest request) {
        LOGGER.debug("Revoking user organization role for request - {}", request);
        final SingleErrorWithStatus<UserRoleErrorResponseModel> error = preconditionChecker.checkRevokeOrganizationAdminRole(request);
        if (error.isPresent()) {
            return new UserRoleRevokeOrganizationAdminResponse(error.getHttpStatus(), error.getError());
        }
        userRoleService.revokeOrganizationAdminRole(new UserRevokeOrganizationAdminRoleDto(request.getUserUuid(), request.getOrganizationUuid()));
        tokenAuthenticationService.expireAllByUser(request.getUserUuid());
        LOGGER.debug("Successfully revoked user organization role for request - {}", request);
        return new UserRoleRevokeOrganizationAdminResponse(request.getUserUuid());
    }

    @Transactional
    @Override
    public UserRoleRevokeClientResponse revokeClientRole(final UserRoleRevokeClientRequest request) {
        LOGGER.debug("Revoking user organization client role for request - {}", request);
        final SingleErrorWithStatus<UserRoleErrorResponseModel> error = preconditionChecker.checkRevokeClientRole(request);
        if (error.isPresent()) {
            return new UserRoleRevokeClientResponse(error.getHttpStatus(), error.getError());
        }
        userRoleService.revokeClientRole(new UserRevokeClientRoleDto(request.getUserUuid(), request.getClientUuid(), UserRole.valueOf(request.getUserRole().name())));
        tokenAuthenticationService.expireAllByUser(request.getUserUuid());
        LOGGER.debug("Successfully revoked user client role for request - {}", request);
        return new UserRoleRevokeClientResponse(request.getUserUuid());
    }

    private void revokeUserOrganizationClients(final String organizationUuid, final String userUuid) {
        final List<String> clientUuids = userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(organizationUuid, userUuid)
                .stream()
                .map(AbstractClientOrganizationAwareUserRole::getClientOrganization)
                .map(ClientOrganization::getUuid)
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(clientUuids)) {
            userRoleService.revokeUserClientsRoles(new UserRevokeClientsRolesDto(userUuid, clientUuids));
        }
    }

    private void revokeIfUserOrganizationRolePresent(final String organizationUuid, final String userUuid) {
        final Optional<AbstractOrganizationAwareUserRole> organizationRoleOptional = userRoleService.findByOrganizationAndUser(organizationUuid, userUuid);
        organizationRoleOptional.ifPresent(organizationRole -> {
            if (organizationRole instanceof UserOrganizationAdminRole) {
                userRoleService.revokeOrganizationAdminRole(
                        new UserRevokeOrganizationAdminRoleDto(userUuid, organizationUuid));
            }
        });
    }
}
