package com.vntana.core.rest.facade.user.role.component.impl;

import com.vntana.commons.api.utils.SingleErrorWithStatus;
import com.vntana.core.domain.client.ClientOrganization;
import com.vntana.core.domain.organization.Organization;
import com.vntana.core.domain.user.AbstractClientOrganizationAwareUserRole;
import com.vntana.core.domain.user.AbstractOrganizationAwareUserRole;
import com.vntana.core.domain.user.User;
import com.vntana.core.domain.user.UserRole;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel;
import com.vntana.core.model.user.role.request.*;
import com.vntana.core.rest.facade.invitation.user.component.UserRolesPermissionsCheckerComponent;
import com.vntana.core.rest.facade.user.role.component.UserRoleFacadePreconditionCheckerComponent;
import com.vntana.core.service.client.OrganizationClientService;
import com.vntana.core.service.organization.OrganizationService;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.role.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.apache.http.HttpStatus.*;

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
    private final UserRolesPermissionsCheckerComponent userRolesPermissionsCheckerComponent;

    public UserRoleFacadePreconditionCheckerComponentImpl(final OrganizationService organizationService,
                                                          final UserService userService,
                                                          final UserRoleService userRoleService,
                                                          final OrganizationClientService clientOrganizationService,
                                                          final UserRolesPermissionsCheckerComponent userRolesPermissionsCheckerComponent) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.organizationService = organizationService;
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.clientOrganizationService = clientOrganizationService;
        this.userRolesPermissionsCheckerComponent = userRolesPermissionsCheckerComponent;
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
        LOGGER.debug("Successfully processed checkGrantOrganizationAdminRole for request - {}", request);
        return SingleErrorWithStatus.empty();
    }

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
        final Optional<Optional<AbstractOrganizationAwareUserRole>> matchOwnerRole = Optional.of(clientOrganizationService.getByUuid(request.getClientUuid()))
                .map(ClientOrganization::getOrganization)
                .map(Organization::getUuid)
                .map(organizationUuid -> userRoleService.findByOrganizationAndUser(organizationUuid, request.getUserUuid()))
                .filter(abstractOrganizationAwareUserRole -> abstractOrganizationAwareUserRole.isPresent()
                        && UserRole.ORGANIZATION_OWNER == abstractOrganizationAwareUserRole.get().getUserRole());
        final SingleErrorWithStatus<UserRoleErrorResponseModel> singleErrorWithStatus = matchOwnerRole.isPresent() ?
                SingleErrorWithStatus.of(SC_CONFLICT, UserRoleErrorResponseModel.REQUESTED_ROLE_ALREADY_GRANTED) : SingleErrorWithStatus.empty();
        LOGGER.debug("Successfully processed checkGrantClientAdminRole for request - {}", request);
        return singleErrorWithStatus;
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

    @Override
    public SingleErrorWithStatus<UserRoleErrorResponseModel> checkUpdateUserOrganizationRoles(final UserUpdateOrganizationRoleRequest request) {
        LOGGER.debug("Processing checkUpdateUserOrganizationRoles for request - {}", request);
        final String organizationUuid = request.getOrganizationUuid();
        final String userUuid = request.getUserUuid();
        final SingleErrorWithStatus<UserRoleErrorResponseModel> error = checkOrganizationAndUserExistence(organizationUuid, userUuid);
        if (error.isPresent()) {
            return error;
        }
        final SingleErrorWithStatus<UserRoleErrorResponseModel> grantOrganizationAdminRoleError = checkGrantToOrganizationOwner(organizationUuid, userUuid);
        if (grantOrganizationAdminRoleError.isPresent()) {
            return grantOrganizationAdminRoleError;
        }
        final SingleErrorWithStatus<UserRoleErrorResponseModel> permissionError = checkAuthorizedUserPermissionForGrantOrganizationAdminRole(organizationUuid, request.getAuthorizedUserUuid());
        if (permissionError.isPresent()) {
            return permissionError;
        }
        LOGGER.debug("Successfully processed checkUpdateUserOrganizationRoles for request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<UserRoleErrorResponseModel> checkUpdateUserOrganizationClientsRoles(final UserUpdateOrganizationClientsRolesRequest request) {
        LOGGER.debug("Processing checkUpdateUserOrganizationClientsRoles for request - {}", request);
        final SingleErrorWithStatus<UserRoleErrorResponseModel> error = checkOrganizationAndUserExistence(request.getOrganizationUuid(), request.getUserUuid());
        if (error.isPresent()) {
            return error;
        }
        final SingleErrorWithStatus<UserRoleErrorResponseModel> grantOrganizationAdminRoleError = checkGrantToOrganizationOwner(request.getOrganizationUuid(), request.getUserUuid());
        if (grantOrganizationAdminRoleError.isPresent()) {
            return grantOrganizationAdminRoleError;
        }
        final Optional<UpdateClientRoleRequest> notFoundClient = request.getUpdateClientRoles().stream()
                .filter(clientRole -> !clientOrganizationService.existsByUuid(clientRole.getClientUuid()))
                .findFirst();
        if (notFoundClient.isPresent()) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, UserRoleErrorResponseModel.CLIENT_ORGANIZATION_NOT_FOUND);
        }
        final Optional<AbstractOrganizationAwareUserRole> authorizedUserOrganizationRole = userRoleService.findByOrganizationAndUser(request.getOrganizationUuid(), request.getAuthorizedUserUuid());
        if (!authorizedUserOrganizationRole.isPresent()) {
            final Optional<UpdateClientRoleRequest> updateClientRolePermissionError = request.getUpdateClientRoles().stream()
                    .filter(updateClientRole -> {
                        final Optional<AbstractClientOrganizationAwareUserRole> authorizedUserClientRole = userRoleService.findByClientOrganizationAndUser(updateClientRole.getClientUuid(), request.getAuthorizedUserUuid());
                        return !authorizedUserClientRole.isPresent() ||
                                !userRolesPermissionsCheckerComponent.isPermittedToGrant(UserRoleModel.valueOf(authorizedUserClientRole.get().getUserRole().name()), updateClientRole.getClientRole());
                    }).findFirst();
            if (updateClientRolePermissionError.isPresent()) {
                return SingleErrorWithStatus.of(SC_FORBIDDEN, UserRoleErrorResponseModel.USER_HAS_NOT_PERMISSION_GRANT_CLIENT_ROLE);
            }
        }
        LOGGER.debug("Successfully processed checkUpdateUserOrganizationClientsRoles for request - {}", request);
        return SingleErrorWithStatus.empty();
    }

    @Override
    public SingleErrorWithStatus<UserRoleErrorResponseModel> checkRevokeOrganizationClientsRoles(final UserRoleRevokeOrganizationClientsRequest request) {
        LOGGER.debug("Processing checkRevokeClientsRolesByUserAndOrganization for request - {}", request);
        final SingleErrorWithStatus<UserRoleErrorResponseModel> error = checkOrganizationAndUserExistence(request.getOrganizationUuid(), request.getUserUuid());
        if (error.isPresent()) {
            return error;
        }
        if (userRoleService.findByOrganizationAndUser(request.getOrganizationUuid(), request.getUserUuid()).isPresent()) {
            return SingleErrorWithStatus.of(SC_CONFLICT, UserRoleErrorResponseModel.REVOCABLE_USER_HAS_ORGANIZATION_ROLE);
        }
        if (userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.getOrganizationUuid(), request.getUserUuid()).isEmpty()) {
            return SingleErrorWithStatus.of(SC_NOT_FOUND, UserRoleErrorResponseModel.REVOCABLE_USER_DOES_NOT_HAVE_CLIENT_ROLE);
        }
        LOGGER.debug("Successfully processed checkRevokeClientsRolesByUserAndOrganization for request - {}", request);
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

    private SingleErrorWithStatus<UserRoleErrorResponseModel> checkAuthorizedUserPermissionForGrantOrganizationAdminRole(final String organizationUuid,
                                                                                                                         final String authorizedUserUuid) {
        final Optional<User> optionalUser = userService.findByUuid(authorizedUserUuid);
        if (optionalUser.isPresent() && optionalUser.get().roleOfSuperAdmin().isPresent()) {
            return SingleErrorWithStatus.empty();
        }
        final Optional<AbstractOrganizationAwareUserRole> authorizedUserOrganizationRole = userRoleService.findByOrganizationAndUser(organizationUuid, authorizedUserUuid);
        if (!authorizedUserOrganizationRole.isPresent()) {
            return SingleErrorWithStatus.of(SC_FORBIDDEN, UserRoleErrorResponseModel.USER_ORGANIZATION_ROLE_NOT_FOUND);
        }
        if (!userRolesPermissionsCheckerComponent.isPermittedToGrant(
                UserRoleModel.valueOf(authorizedUserOrganizationRole.get().getUserRole().name()),
                UserRoleModel.ORGANIZATION_ADMIN)
        ) {
            return SingleErrorWithStatus.of(SC_FORBIDDEN, UserRoleErrorResponseModel.USER_HAS_NOT_PERMISSION_GRANT_ORGANIZATION_ROLE);
        }
        return SingleErrorWithStatus.empty();
    }

    private SingleErrorWithStatus<UserRoleErrorResponseModel> checkGrantToOrganizationOwner(final String organizationUuid,
                                                                                            final String userUuid) {
        final Optional<AbstractOrganizationAwareUserRole> organizationUserRoleOptional = userRoleService.findByOrganizationAndUser(organizationUuid, userUuid);
        if (organizationUserRoleOptional.isPresent()) {
            final AbstractOrganizationAwareUserRole organizationUserRole = organizationUserRoleOptional.get();
            if (organizationUserRole.getUserRole() == UserRole.ORGANIZATION_OWNER) {
                return SingleErrorWithStatus.of(SC_CONFLICT, UserRoleErrorResponseModel.REQUESTED_USER_IS_ORGANIZATION_OWNER);
            }
        }
        return SingleErrorWithStatus.empty();
    }
}
