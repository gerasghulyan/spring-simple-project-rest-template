package com.vntana.core.rest.facade.user.role.component.impl;

import com.vntana.core.domain.user.AbstractClientOrganizationAwareUserRole;
import com.vntana.core.domain.user.AbstractOrganizationAwareUserRole;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.user.role.request.UpdateClientRoleRequest;
import com.vntana.core.model.user.role.request.UserUpdateOrganizationClientsRolesRequest;
import com.vntana.core.rest.facade.user.role.component.UserRoleHelperComponent;
import com.vntana.core.service.user.role.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Vardan Aivazian
 * Date: 15.12.2020
 * Time: 12:40
 */
@Component
public class UserRoleHelperComponentImpl implements UserRoleHelperComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleHelperComponentImpl.class);

    private final UserRoleService userRoleService;

    public UserRoleHelperComponentImpl(final UserRoleService userRoleService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userRoleService = userRoleService;
    }

    @Override
    public List<UpdateClientRoleRequest> fetchRevokeRolesFromUpdateRolesRequest(final UserUpdateOrganizationClientsRolesRequest request) {
        LOGGER.debug("Processing fetchRevokeRolesFromUpdateRolesRequest for request - {}", request);
        final List<UpdateClientRoleRequest> updatedClientRoles = request.getUpdateClientRoles();
        final List<AbstractClientOrganizationAwareUserRole> existedClientRoles = getVisibleUserClientOrganizations(request.getOrganizationUuid(), request.getRequestedUserUuid(), request.getUuid());
        final Set<String> updatedClientRolesUuids = updatedClientRoles.stream()
                .map(UpdateClientRoleRequest::getClientUuid)
                .collect(Collectors.toSet());
        final List<UpdateClientRoleRequest> revokeResultList = existedClientRoles.stream()
                .filter(existedClientRole -> !updatedClientRolesUuids.contains(existedClientRole.getClientOrganization().getUuid()))
                .map(this::buildUpdateClientRoleRequest)
                .collect(Collectors.toList());
        LOGGER.debug("Successfully processed fetchRevokeRolesFromUpdateRolesRequest for request - {}", request);
        return revokeResultList;
    }

    @Override
    public List<UpdateClientRoleRequest> fetchGrantRolesFromUpdateRolesRequest(final UserUpdateOrganizationClientsRolesRequest request) {
        LOGGER.debug("Processing fetchGrantRolesFromUpdateRolesRequest for request - {}", request);
        final List<UpdateClientRoleRequest> updatedClientRoles = request.getUpdateClientRoles();
        final List<AbstractClientOrganizationAwareUserRole> existedClientRoles = getVisibleUserClientOrganizations(request.getOrganizationUuid(), request.getRequestedUserUuid(), request.getUuid());
        final List<UpdateClientRoleRequest> grantResultList = CollectionUtils.isEmpty(existedClientRoles) ? updatedClientRoles :
                updatedClientRoles.stream()
                        .filter(updatedClientRole -> existedClientRoles.stream()
                                .anyMatch(existedClientRole ->
                                        (!existedClientRole.getClientOrganization().getUuid().equals(updatedClientRole.getClientUuid()) ||
                                                (existedClientRole.getClientOrganization().getUuid().equals(updatedClientRole.getClientUuid()) &&
                                                        UserRoleModel.valueOf(existedClientRole.getUserRole().name()) != updatedClientRole.getClientRole()))))
                        .collect(Collectors.toList());
        LOGGER.debug("Successfully processed fetchGrantRolesFromUpdateRolesRequest for request - {}", request);
        return grantResultList;
    }

    private List<AbstractClientOrganizationAwareUserRole> getVisibleUserClientOrganizations(final String organizationUuid, final String userUuid, final String authorizedUserUuid) {
        LOGGER.debug("Getting user - {} client organizations visible for authorized user - {}", userUuid, authorizedUserUuid);
        final List<AbstractClientOrganizationAwareUserRole> existedUserClientsRoles = userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(organizationUuid, userUuid);
        final Optional<AbstractOrganizationAwareUserRole> organizationRole = userRoleService.findByOrganizationAndUser(organizationUuid, authorizedUserUuid);
        if (organizationRole.isPresent()) {
            return existedUserClientsRoles;
        }
        final List<AbstractClientOrganizationAwareUserRole> organizationClientsRoles = userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(organizationUuid, authorizedUserUuid);
        final List<AbstractClientOrganizationAwareUserRole> clientRolesResult = existedUserClientsRoles.stream()
                .filter(existedUserClientsRole -> organizationClientsRoles.stream()
                        .anyMatch(organizationClientsRole ->
                                existedUserClientsRole.getClientOrganization().getUuid().equals(organizationClientsRole.getClientOrganization().getUuid())))
                .collect(Collectors.toList());
        LOGGER.debug("Successfully got user - {} client organizations visible for authorized user - {}, clientRolesResult -{}", userUuid, authorizedUserUuid, clientRolesResult);
        return clientRolesResult;
    }
    
    private UpdateClientRoleRequest buildUpdateClientRoleRequest(final AbstractClientOrganizationAwareUserRole clientOrganizationRole) {
        return new UpdateClientRoleRequest(clientOrganizationRole.getClientOrganization().getUuid(), UserRoleModel.valueOf(clientOrganizationRole.getUserRole().name()));
    }
}
