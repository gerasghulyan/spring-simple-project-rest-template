package com.vntana.core.rest.facade.user.role.component.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import com.vntana.core.model.user.role.request.*;
import com.vntana.core.rest.facade.user.role.component.UserRoleFacadePreconditionCheckerComponent;
import com.vntana.core.service.client.ClientOrganizationService;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.role.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 11:34 AM
 */
@Component
public class UserRoleFacadePreconditionCheckerComponentImpl implements UserRoleFacadePreconditionCheckerComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleFacadePreconditionCheckerComponentImpl.class);

    private final OrganizationService organizationService;
    private final ClientOrganizationService clientOrganizationService;
    private final UserService userService;
    private final UserRoleService userRoleService;

    public UserRoleFacadePreconditionCheckerComponentImpl(final OrganizationService organizationService,
                                                          final UserService userService,
                                                          final UserRoleService userRoleService,
                                                          final ClientOrganizationService clientOrganizationService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.organizationService = organizationService;
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.clientOrganizationService = clientOrganizationService;
    }

    @Override
    public SingleErrorWithStatus<UserRoleErrorResponseModel> checkGrantSuperAdmin(final UserRoleGrantSuperAdminRequest request) {
        LOGGER.debug("Processing checkGrantSuperAdmin for request - {}", request);
        final Optional<User> userOptional = userService.findByUuid(request.getUserUuid());
        if (!userOptional.isPresent()) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, UserRoleErrorResponseModel.USER_NOT_FOUND);
        }
        if (userOptional.get().roleOfSuperAdmin().isPresent()) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, UserRoleErrorResponseModel.REQUESTED_ROLE_ALREADY_GRANTED);
        }
        LOGGER.debug("Successfully processed checkGrantSuperAdmin for request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<UserRoleErrorResponseModel> checkGrantOrganizationAdminRole(final UserRoleGrantOrganizationAdminRequest request) {
        LOGGER.debug("Processing checkGrantOrganizationAdminRole for request - {}", request);
        final SingleErrorWithStatus<UserRoleErrorResponseModel> error = checkOrganizationAndUserExistence(request.getOrganizationUuid(), request.getUserUuid());
        if (error.isPresent()) {
            return error;
        }
        if (userRoleService.existsByOrganizationAndUserAndRole(request.getOrganizationUuid(), request.getUserUuid(), UserRole.ORGANIZATION_ADMIN)) {
            return SingleErrorWithStatus.of(SC_CONFLICT, UserRoleErrorResponseModel.REQUESTED_ROLE_ALREADY_GRANTED);
        }
        LOGGER.debug("Successfully processed checkGrantOrganizationAdminRole for request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<UserRoleErrorResponseModel> checkGrantClientAdminRole(final UserRoleGrantClientAdminOrganizationRequest request) {
        LOGGER.debug("Processing checkGrantClientAdminRole for request - {}", request);
        final SingleErrorWithStatus<UserRoleErrorResponseModel> error = checkClientAndUserExistence(request.getClientOrganizationUuid(), request.getUserUuid());
        if (error.isPresent()) {
            return error;
        }
        LOGGER.debug("Successfully processed checkGrantClientAdminRole for request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<UserRoleErrorResponseModel> checkRevokeOrganizationAdminRole(final UserRoleRevokeOrganizationAdminRequest request) {
        LOGGER.debug("Processing checkRevokeOrganizationAdminRole for request - {}", request);
        final SingleErrorWithStatus<UserRoleErrorResponseModel> error = checkOrganizationAndUserExistence(request.getOrganizationUuid(), request.getUserUuid());
        if (error.isPresent()) {
            return error;
        }
        if (!userRoleService.existsByOrganizationAndUserAndRole(request.getOrganizationUuid(), request.getUserUuid(), UserRole.ORGANIZATION_ADMIN)) {
            return SingleErrorWithStatus.of(SC_CONFLICT, UserRoleErrorResponseModel.REQUESTED_ROLE_IS_ABSENT);
        }
        LOGGER.debug("Successfully processed checkRevokeOrganizationAdminRole for request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<UserRoleErrorResponseModel> checkRevokeClientAdminRole(final UserRoleRevokeClientAdminRequest request) {
        LOGGER.debug("Processing checkRevokeClientAdminRole for request - {}", request);
        final SingleErrorWithStatus<UserRoleErrorResponseModel> error = checkClientAndUserExistence(request.getClientOrganizationUuid(), request.getUserUuid());
        if (error.isPresent()) {
            return error;
        }
        // TODO: 11/5/20 add user role service check if needed 
        LOGGER.debug("Successfully processed checkRevokeClientAdminRole for request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    private SingleErrorWithStatus<UserRoleErrorResponseModel> checkOrganizationAndUserExistence(final String organizationUuid,
                                                                                                final String userUuid) {
        if (!organizationService.existsByUuid(organizationUuid)) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, UserRoleErrorResponseModel.ORGANIZATION_NOT_FOUND);
        }
        if (!userService.existsByUuid(userUuid)) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, UserRoleErrorResponseModel.USER_NOT_FOUND);
        }
        return SingleErrorWithStatus.empty();
    }

    private SingleErrorWithStatus<UserRoleErrorResponseModel> checkClientAndUserExistence(final String clientOrganizationUuid,
                                                                                          final String userUuid) {
        if (!clientOrganizationService.existsByUuid(clientOrganizationUuid)) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, UserRoleErrorResponseModel.CLIENT_ORGANIZATION_NOT_FOUND);
        }
        if (!userService.existsByUuid(userUuid)) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, UserRoleErrorResponseModel.USER_NOT_FOUND);
        }
        return SingleErrorWithStatus.empty();
    }
}
