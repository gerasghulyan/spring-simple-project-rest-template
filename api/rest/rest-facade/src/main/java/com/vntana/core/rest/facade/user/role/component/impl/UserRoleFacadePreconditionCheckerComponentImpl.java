package com.vntana.core.rest.facade.user.role.component.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import com.vntana.core.model.user.role.request.*;
import com.vntana.core.rest.facade.user.role.component.UserRoleFacadePreconditionCheckerComponent;
import com.vntana.core.service.client.OrganizationClientService;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.role.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    private final OrganizationClientService clientOrganizationService;
    private final UserService userService;
    private final UserRoleService userRoleService;

    public UserRoleFacadePreconditionCheckerComponentImpl(final OrganizationService organizationService,
                                                          final UserService userService,
                                                          final UserRoleService userRoleService,
                                                          final OrganizationClientService clientOrganizationService) {
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
        if (userRoleService.findByOrganizationAndUser(request.getOrganizationUuid(), request.getUserUuid()).isPresent()) {
            return SingleErrorWithStatus.of(SC_CONFLICT, UserRoleErrorResponseModel.REQUESTED_ROLE_ALREADY_GRANTED);
        }
        if(!userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.getOrganizationUuid(), request.getUserUuid()).isEmpty()){
            return SingleErrorWithStatus.of(SC_CONFLICT, UserRoleErrorResponseModel.REQUESTED_ROLE_ALREADY_GRANTED);
        }
        LOGGER.debug("Successfully processed checkGrantOrganizationAdminRole for request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Transactional(readOnly = true)
    @Override
    public SingleErrorWithStatus<UserRoleErrorResponseModel> checkGrantClientRole(final UserRoleGrantClientOrganizationRequest request) {
        LOGGER.debug("Processing checkGrantClientAdminRole for request - {}", request);
        final SingleErrorWithStatus<UserRoleErrorResponseModel> error = checkClientAndUserExistence(request.getClientUuid(), request.getUserUuid());
        if (error.isPresent()) {
            return error;
        }
        if (userRoleService.findByClientOrganizationAndUser(request.getClientUuid(), request.getUserUuid()).isPresent()) {
            return SingleErrorWithStatus.of(SC_CONFLICT, UserRoleErrorResponseModel.REQUESTED_ROLE_ALREADY_GRANTED);
        }
        final Organization organization = clientOrganizationService.findByUuid(request.getClientUuid()).get().getOrganization();
        if (userRoleService.findByOrganizationAndUser(organization.getUuid(), request.getUserUuid()).isPresent()) {
            return SingleErrorWithStatus.of(SC_CONFLICT, UserRoleErrorResponseModel.REQUESTED_ROLE_ALREADY_GRANTED);
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
    public SingleErrorWithStatus<UserRoleErrorResponseModel> checkRevokeClientRole(final UserRoleRevokeClientRequest request) {
        LOGGER.debug("Processing checkRevokeClientAdminRole for request - {}", request);
        final SingleErrorWithStatus<UserRoleErrorResponseModel> error = checkClientAndUserExistence(request.getClientUuid(), request.getUserUuid());
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
