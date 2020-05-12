package com.vntana.core.rest.facade.user.role.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.user.UserOrganizationAdminRole;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import com.vntana.core.model.user.role.request.UserRoleGrantOrganizationAdminRequest;
import com.vntana.core.model.user.role.request.UserRoleRevokeOrganizationAdminRequest;
import com.vntana.core.model.user.role.response.UserRoleGrantOrganizationAdminResponse;
import com.vntana.core.model.user.role.response.UserRoleRevokeOrganizationAdminResponse;
import com.vntana.core.rest.facade.user.role.UserRoleServiceFacade;
import com.vntana.core.rest.facade.user.role.component.UserRoleFacadePreconditionCheckerComponent;
import com.vntana.core.service.user.role.UserRoleService;
import com.vntana.core.service.user.role.dto.UserGrantOrganizationRoleDto;
import com.vntana.core.service.user.role.dto.UserRevokeOrganizationAdminRoleDto;
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
    private final UserRoleService userRoleService;

    public UserRoleServiceFacadeImpl(final UserRoleFacadePreconditionCheckerComponent preconditionChecker, final UserRoleService userRoleService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.preconditionChecker = preconditionChecker;
        this.userRoleService = userRoleService;
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

    @Transactional
    @Override
    public UserRoleRevokeOrganizationAdminResponse revokeOrganizationAdminRole(final UserRoleRevokeOrganizationAdminRequest request) {
        LOGGER.debug("Revoking user organization role for request - {}", request);
        final SingleErrorWithStatus<UserRoleErrorResponseModel> error = preconditionChecker.checkRevokeOrganizationAdminRole(request);
        if (error.isPresent()) {
            return new UserRoleRevokeOrganizationAdminResponse(error.getHttpStatus(), error.getError());
        }
        userRoleService.revokeOrganizationAdminRole(new UserRevokeOrganizationAdminRoleDto(request.getUserUuid(), request.getOrganizationUuid()));
        LOGGER.debug("Successfully revoked user organization role for request - {}", request);
        return new UserRoleRevokeOrganizationAdminResponse(request.getUserUuid());
    }
}
