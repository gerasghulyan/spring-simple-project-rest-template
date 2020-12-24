package com.vntana.core.rest.facade.invitation.user.component.impl;

import com.vntana.core.domain.user.AbstractClientOrganizationAwareUserRole;
import com.vntana.core.domain.user.User;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.user.role.request.UpdateClientRoleRequest;
import com.vntana.core.model.user.role.request.UserUpdateOrganizationClientsRolesRequest;
import com.vntana.core.model.user.role.request.UserUpdateOrganizationRoleRequest;
import com.vntana.core.rest.facade.invitation.user.component.UserRolesPermissionsCheckerComponent;
import com.vntana.core.service.user.UserService;
import com.vntana.core.service.user.role.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by Diana Gevorgyan
 * Date: 11/12/20
 * Time: 3:05 PM
 */
@Component
public class UserRolesPermissionsCheckerComponentImpl implements UserRolesPermissionsCheckerComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRolesPermissionsCheckerComponentImpl.class);

    private final UserRoleService userRoleService;
    private final UserService userService;

    public UserRolesPermissionsCheckerComponentImpl(final UserService userService,
                                                    final UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
    }

    @Override
    public boolean isPermittedToInvite(final UserRoleModel inviter, final UserRoleModel invited) {
        if (!inviter.hasInviterAbility()) {
            return false;
        }
        if (inviter == UserRoleModel.ORGANIZATION_OWNER) {
            return invited != UserRoleModel.ORGANIZATION_OWNER;
        }
        return inviter.getPriority() <= invited.getPriority();
    }

    @Override
    public boolean isPermittedToGrant(final UserRoleModel granter, final UserRoleModel granted) {
        return granter.hasGranterAbility() && granted.hasGrantedAbility() &&
                (granter.getPriority() <= granted.getPriority());
    }

    public boolean isPermittedGrantTo(final UserRoleModel granter, final UserRoleModel toGranted) {
        return granter.hasGranterAbility() && toGranted.hasGrantedAbility() &&
                (granter.getPriority() <= toGranted.getPriority());
    }

    @Override
    public boolean isPermittedToUpdateOrganizationRole(final UserUpdateOrganizationRoleRequest request) {
        final Optional<User> optionalUser = userService.findByUuid(request.getUuid());
        if (optionalUser.isPresent() && optionalUser.get().roleOfSuperAdmin().isPresent()) {
            return true;
        }
        return userRoleService.findByOrganizationAndUser(request.getOrganizationUuid(), request.getUuid()).
                filter(organizationUserRole -> isPermittedToGrant(
                        UserRoleModel.valueOf(organizationUserRole.getUserRole().name()),
                        UserRoleModel.ORGANIZATION_ADMIN))
                .isPresent();
    }

    @Override
    public boolean isPermittedClientUserToUpdateClientRole(final UserUpdateOrganizationClientsRolesRequest request) {
        return request.getUpdateClientRoles().stream()
                .allMatch(updateClientRole ->
                        isPermittedToUpdateClientRole(updateClientRole, request.getUuid(), request.getRequestedUserUuid()));
    }

    private boolean isPermittedToUpdateClientRole(final UpdateClientRoleRequest updateClientRole, final String userUuid, final String requestedUserUuid) {
        final Optional<AbstractClientOrganizationAwareUserRole> authorizedUserClientRole = userRoleService.findByClientOrganizationAndUser(updateClientRole.getClientUuid(), userUuid);
        if (authorizedUserClientRole.isPresent()) {
            final Optional<AbstractClientOrganizationAwareUserRole> requestedUserClientRole = userRoleService.findByClientOrganizationAndUser(updateClientRole.getClientUuid(), requestedUserUuid);
            if (requestedUserClientRole.isPresent()) {
                final UserRoleModel requestedUserClientRoleModel = UserRoleModel.valueOf(requestedUserClientRole.get().getUserRole().name());
                return updateClientRole.getClientRole() != requestedUserClientRoleModel
                        && isPermittedToGrant(UserRoleModel.valueOf(authorizedUserClientRole.get().getUserRole().name()), updateClientRole.getClientRole())
                        && isPermittedGrantTo(UserRoleModel.valueOf(authorizedUserClientRole.get().getUserRole().name()), requestedUserClientRoleModel);
            }
            return isPermittedToGrant(UserRoleModel.valueOf(authorizedUserClientRole.get().getUserRole().name()), updateClientRole.getClientRole());
        }
        return false;
    }
}
