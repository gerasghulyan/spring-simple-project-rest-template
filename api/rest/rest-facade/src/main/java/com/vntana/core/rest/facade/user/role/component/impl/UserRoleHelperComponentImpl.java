package com.vntana.core.rest.facade.user.role.component.impl;

import com.vntana.core.domain.user.AbstractClientOrganizationAwareUserRole;
import com.vntana.core.domain.user.AbstractOrganizationAwareUserRole;
import com.vntana.core.model.auth.response.UserRoleModel;
import com.vntana.core.model.user.role.request.UpdateClientRoleRequest;
import com.vntana.core.model.user.role.request.UpdatedClientRoleModel;
import com.vntana.core.model.user.role.request.UserUpdateOrganizationClientsRolesRequest;
import com.vntana.core.rest.facade.user.role.component.UserRoleActionItemRetrieverComponent;
import com.vntana.core.service.user.role.UserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Vardan Aivazian
 * Date: 15.12.2020
 * Time: 12:40
 */
@Component
public class UserRoleHelperComponentImpl implements UserRoleActionItemRetrieverComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleHelperComponentImpl.class);

    private final UserRoleService userRoleService;

    public UserRoleHelperComponentImpl(final UserRoleService userRoleService) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userRoleService = userRoleService;
    }

    @Override
    public List<UpdateClientRoleRequest> fetchRevokedRolesFromUpdateRolesRequest(final UserUpdateOrganizationClientsRolesRequest request) {
        LOGGER.debug("Processing fetchRevokedRolesFromUpdateRolesRequest for request - {}", request);
        final List<UpdateClientRoleRequest> updatedClientRoles = request.getUpdateClientRoles();
        final List<AbstractClientOrganizationAwareUserRole> existedClientRoles = getRequestAndAuthorizedUsersVisibleClientOrganizations(request.getOrganizationUuid(), request.getRequestedUserUuid(), request.getUuid());
        final Set<String> updatedClientRolesUuids = updatedClientRoles.stream()
                .map(UpdateClientRoleRequest::getClientUuid)
                .collect(Collectors.toSet());
        final List<UpdateClientRoleRequest> revokeResultList = existedClientRoles.stream()
                .filter(existedClientRole -> !updatedClientRolesUuids.contains(existedClientRole.getClientOrganization().getUuid()))
                .map(this::buildUpdateClientRoleRequest)
                .collect(Collectors.toList());
        LOGGER.debug("Successfully processed fetchRevokedRolesFromUpdateRolesRequest for request - {}", request);
        return revokeResultList;
    }

    @Override
    public List<UpdateClientRoleRequest> fetchGrantedRolesFromUpdateRolesRequest(final UserUpdateOrganizationClientsRolesRequest request) {
        LOGGER.debug("Processing fetchGrantedRolesFromUpdateRolesRequest for request - {}", request);
        final List<UpdateClientRoleRequest> updatedClientRoles = request.getUpdateClientRoles();
        final List<AbstractClientOrganizationAwareUserRole> existedClientRoles = getRequestAndAuthorizedUsersVisibleClientOrganizations(request.getOrganizationUuid(), request.getRequestedUserUuid(), request.getUuid());
        final List<UpdateClientRoleRequest> grantResultList = CollectionUtils.isEmpty(existedClientRoles) ? updatedClientRoles :
                updatedClientRoles.stream()
                        .filter(updatedClientRole -> existedClientRoles.stream()
                                .noneMatch(existedClientRole -> existedClientRole.getClientOrganization().getUuid().equals(updatedClientRole.getClientUuid())))
                        .collect(Collectors.toList());
        LOGGER.debug("Successfully processed fetchGrantedRolesFromUpdateRolesRequest for request - {}", request);
        return grantResultList;
    }

    @Override
    public List<UpdatedClientRoleModel> fetchUpdatedRolesFromUpdateRolesRequest(final UserUpdateOrganizationClientsRolesRequest request) {
        LOGGER.debug("Processing fetchUpdatedRolesFromUpdateRolesRequest for request - {}", request);
        final List<UpdateClientRoleRequest> updateClientRoles = request.getUpdateClientRoles();
        final List<AbstractClientOrganizationAwareUserRole> existedClientRoles = getRequestAndAuthorizedUsersVisibleClientOrganizations(request.getOrganizationUuid(), request.getRequestedUserUuid(), request.getUuid());
        final Map<String, UserRoleModel> updatedRolesMap = existedClientRoles.stream()
                .filter(existedClientRole -> updateClientRoles.stream()
                        .anyMatch(updateClientRole ->
                                (updateClientRole.getClientUuid().equals(existedClientRole.getClientOrganization().getUuid()) &&
                                        updateClientRole.getClientRole() != UserRoleModel.valueOf(existedClientRole.getUserRole().name()))))
                .collect(Collectors.toMap(
                        existedClientRole -> existedClientRole.getClientOrganization().getUuid(),
                        existedClientRole -> UserRoleModel.valueOf(existedClientRole.getUserRole().name())
                ));
        final List<UpdatedClientRoleModel> updatedResultList = updateClientRoles.stream()
                .filter(updateRole -> updatedRolesMap.containsKey(updateRole.getClientUuid()))
                .map(updateRole -> new UpdatedClientRoleModel(updateRole.getClientUuid(), updatedRolesMap.get(updateRole.getClientUuid()), updateRole.getClientRole()))
                .collect(Collectors.toList());
        LOGGER.debug("Successfully processed fetchUpdatedRolesFromUpdateRolesRequest for request - {}", request);
        return updatedResultList;
    }

    private List<AbstractClientOrganizationAwareUserRole> getRequestAndAuthorizedUsersVisibleClientOrganizations(final String organizationUuid, final String requestedUserUuid, final String userUuid) {
        LOGGER.debug("Getting user - {} client organizations visible for authorized user - {}", requestedUserUuid, userUuid);
        final List<AbstractClientOrganizationAwareUserRole> existedUserClientsRoles = userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(organizationUuid, requestedUserUuid);
        final Optional<AbstractOrganizationAwareUserRole> organizationRole = userRoleService.findByOrganizationAndUser(organizationUuid, userUuid);
        if (organizationRole.isPresent()) {
            return existedUserClientsRoles;
        }
        final List<AbstractClientOrganizationAwareUserRole> organizationClientsRoles = userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(organizationUuid, userUuid);
        final List<AbstractClientOrganizationAwareUserRole> clientRolesResult = existedUserClientsRoles.stream()
                .filter(existedUserClientsRole -> organizationClientsRoles.stream()
                        .anyMatch(organizationClientsRole ->
                                existedUserClientsRole.getClientOrganization().getUuid().equals(organizationClientsRole.getClientOrganization().getUuid())))
                .collect(Collectors.toList());
        LOGGER.debug("Successfully got user - {} client organizations visible for authorized user - {}, clientRolesResult -{}", requestedUserUuid, userUuid, clientRolesResult);
        return clientRolesResult;
    }

    private UpdateClientRoleRequest buildUpdateClientRoleRequest(final AbstractClientOrganizationAwareUserRole clientOrganizationRole) {
        return new UpdateClientRoleRequest(clientOrganizationRole.getClientOrganization().getUuid(), UserRoleModel.valueOf(clientOrganizationRole.getUserRole().name()));
    }
}
