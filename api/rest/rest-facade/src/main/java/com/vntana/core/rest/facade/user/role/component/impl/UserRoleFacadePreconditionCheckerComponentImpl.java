package com.vntana.core.rest.facade.user.role.component.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import com.vntana.core.model.user.role.request.UserRoleGrantOrganizationAdminRequest;
import com.vntana.core.model.user.role.request.UserRoleRevokeOrganizationAdminRequest;
import com.vntana.core.rest.facade.user.role.component.UserRoleFacadePreconditionCheckerComponent;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.role.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
    private final UserService userService;
    private final UserRoleService userRoleService;

    public UserRoleFacadePreconditionCheckerComponentImpl(final OrganizationService organizationService,
                                                          final UserService userService,
                                                          final UserRoleService userRoleService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.organizationService = organizationService;
        this.userService = userService;
        this.userRoleService = userRoleService;
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

}
