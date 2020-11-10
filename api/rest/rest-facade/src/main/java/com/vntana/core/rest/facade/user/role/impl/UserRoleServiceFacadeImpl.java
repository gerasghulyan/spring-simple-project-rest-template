package com.vntana.core.rest.facade.user.role.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.user.AbstractUserRole;
import com.vntana.core.domain.user.UserOrganizationAdminRole;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import com.vntana.core.model.user.role.request.*;
import com.vntana.core.model.user.role.response.*;
import com.vntana.core.rest.facade.user.role.UserRoleServiceFacade;
import com.vntana.core.rest.facade.user.role.component.UserRoleFacadePreconditionCheckerComponent;
import com.vntana.core.service.token.auth.AuthTokenService;
import com.vntana.core.service.user.role.UserRoleService;
import com.vntana.core.service.user.role.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 11:31 AM
 */
@Component
public class UserRoleServiceFacadeImpl implements UserRoleServiceFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleServiceFacadeImpl.class);

    private final UserRoleFacadePreconditionCheckerComponent preconditionChecker;
    private final AuthTokenService authTokenService;
    private final UserRoleService userRoleService;

    public UserRoleServiceFacadeImpl(final UserRoleFacadePreconditionCheckerComponent preconditionChecker,
                                     final AuthTokenService authTokenService,
                                     final UserRoleService userRoleService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.preconditionChecker = preconditionChecker;
        this.authTokenService = authTokenService;
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
        final UserOrganizationAdminRole adminRole = userRoleService.grantOrganizationAdminRole(new UserGrantOrganizationRoleDto(request.getUserUuid(), request.getOrganizationUuid()));
        LOGGER.debug("Successfully granted user organization role for request - {}", request);
        return new UserRoleGrantOrganizationAdminResponse(adminRole.getUser().getUuid());
    }

    @Override
    public UserRoleGrantClientAdminResponse grantClientAdminRole(final UserRoleGrantClientAdminRequest request) {
        LOGGER.debug("Granting user client role for request - {}", request);
        final SingleErrorWithStatus<UserRoleErrorResponseModel> error = preconditionChecker.checkGrantClientAdminRole(request);
        if (error.isPresent()) {
            return new UserRoleGrantClientAdminResponse(error.getHttpStatus(), error.getError());
        }
        final AbstractUserRole clientRole = userRoleService.grantClientRole(new UserGrantClientRoleDto(request.getUserUuid(), request.getClientOrganizationUuid(), UserRole.CLIENT_ORGANIZATION_ADMIN));
        LOGGER.debug("Successfully granted user organization client role for request - {}", request);
        return new UserRoleGrantClientAdminResponse(clientRole.getUser().getUuid());
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
        authTokenService.expireAllByUser(request.getUserUuid());
        LOGGER.debug("Successfully revoked user organization role for request - {}", request);
        return new UserRoleRevokeOrganizationAdminResponse(request.getUserUuid());
    }

    @Transactional
    @Override
    public UserRoleRevokeClientAdminResponse revokeClientAdminRole(final UserRoleRevokeClientAdminRequest request) {
        LOGGER.debug("Revoking user organization client role for request - {}", request);
        final SingleErrorWithStatus<UserRoleErrorResponseModel> error = preconditionChecker.checkRevokeClientAdminRole(request);
        if (error.isPresent()) {
            return new UserRoleRevokeClientAdminResponse(error.getHttpStatus(), error.getError());
        }
        userRoleService.revokeClientRole(new UserRevokeClientRoleDto(request.getUserUuid(), request.getClientOrganizationUuid(), UserRole.CLIENT_ORGANIZATION_ADMIN));
        authTokenService.expireAllByUser(request.getUserUuid());
        LOGGER.debug("Successfully revoked user client role for request - {}", request);
        return new UserRoleRevokeClientAdminResponse(request.getUserUuid());
    }
}
