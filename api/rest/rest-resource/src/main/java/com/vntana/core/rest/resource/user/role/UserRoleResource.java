package com.vntana.core.rest.resource.user.role;

import com.vntana.commons.web.utils.ResponseEntityUtils;
import com.vntana.core.model.user.role.request.*;
import com.vntana.core.model.user.role.response.*;
import com.vntana.core.rest.facade.user.role.UserRoleServiceFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 2:49 PM
 */
@RestController
@RequestMapping(value = "/user-roles",
        produces = "application/json"
)
public class UserRoleResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleResource.class);

    private final UserRoleServiceFacade userRoleServiceFacade;

    public UserRoleResource(final UserRoleServiceFacade userRoleServiceFacade) {
        LOGGER.debug("Initializing - {}", getClass().getCanonicalName());
        this.userRoleServiceFacade = userRoleServiceFacade;
    }

    @PostMapping("super-admin")
    public ResponseEntity<UserRoleGrantSuperAdminResponse> grantSuperAdmin(@RequestBody final UserRoleGrantSuperAdminRequest request) {
        LOGGER.debug("Processing user-roles resource grantSuperAdmin for request - {}", request);
        final UserRoleGrantSuperAdminResponse response = userRoleServiceFacade.grantSuperAdmin(request);
        LOGGER.debug("Successfully processing user-roles resource grantSuperAdmin for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(response);
    }

    @PostMapping("organization-admin")
    public ResponseEntity<UserRoleGrantOrganizationAdminResponse> grantUserOrganizationAdminRole(@RequestBody final UserRoleGrantOrganizationAdminRequest request) {
        LOGGER.debug("Processing user-roles resource grantUserOrganizationAdminRole for request - {}", request);
        final UserRoleGrantOrganizationAdminResponse userRoleGrantOrganizationAdminResponse = userRoleServiceFacade.grantOrganizationAdminRole(request);
        LOGGER.debug("Successfully processing user-roles resource grantUserOrganizationAdminRole for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(userRoleGrantOrganizationAdminResponse);
    }

    @PostMapping("client")
    public ResponseEntity<UserRoleGrantClientOrganizationResponse> grantUserClientRole(@RequestBody final UserRoleGrantClientOrganizationRequest request) {
        LOGGER.debug("Processing user-roles resource grantUserClientRole for request - {}", request);
        final UserRoleGrantClientOrganizationResponse userRoleGrantClientOrganizationResponse = userRoleServiceFacade.grantClientRole(request);
        LOGGER.debug("Successfully processing user-roles resource grantUserClientRole for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(userRoleGrantClientOrganizationResponse);
    }

    @DeleteMapping("organization-admin")
    public ResponseEntity<UserRoleRevokeOrganizationAdminResponse> revokeUserOrganizationAdminRole(@RequestBody final UserRoleRevokeOrganizationAdminRequest request) {
        LOGGER.debug("Processing user-roles resource revokeUserOrganizationAdminRole for request - {}", request);
        final UserRoleRevokeOrganizationAdminResponse userRoleRevokeOrganizationAdminResponse = userRoleServiceFacade.revokeOrganizationAdminRole(request);
        LOGGER.debug("Successfully processing user-roles resource revokeUserOrganizationAdminRole for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(userRoleRevokeOrganizationAdminResponse);
    }

    @DeleteMapping("client")
    public ResponseEntity<UserRoleRevokeClientResponse> revokeUserClientRole(@RequestBody final UserRoleRevokeClientRequest request) {
        LOGGER.debug("Processing user-roles resource revokeUserClientRole for request - {}", request);
        final UserRoleRevokeClientResponse userRoleRevokeClientResponse = userRoleServiceFacade.revokeClientRole(request);
        LOGGER.debug("Successfully processing user-roles resource revokeUserClientRole for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(userRoleRevokeClientResponse);
    }

    @DeleteMapping("organizations/clients")
    public ResponseEntity<UserRoleRevokeOrganizationClientsResponse> revokeUserOrganizationClientsRoles(@RequestBody final UserRoleRevokeOrganizationClientsRequest request) {
        LOGGER.debug("Processing user-roles resource revokeUserOrganizationClientsRoles for request - {}", request);
        final UserRoleRevokeOrganizationClientsResponse userOrganizationClientsResponse = userRoleServiceFacade.revokeOrganizationClientsRoles(request);
        LOGGER.debug("Successfully processing user-roles resource revokeUserOrganizationClientsRoles for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(userOrganizationClientsResponse);
    }

    @PutMapping("organization-role")
    public ResponseEntity<UserUpdateRolesResponse> updateUserOrganizationRole(@RequestBody final UserUpdateOrganizationRoleRequest request) {
        LOGGER.debug("Processing update user roles for request - {}", request);
        final UserUpdateRolesResponse userUpdateRolesResponse = userRoleServiceFacade.updateUserOrganizationRole(request);
        LOGGER.debug("Successfully proceeded update user roles for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(userUpdateRolesResponse);
    }
    
    @PutMapping("organization-clients-role")
    public ResponseEntity<UserUpdateRolesResponse> updateUserOrganizationClientsRoles(@RequestBody final UserUpdateOrganizationClientsRolesRequest request) {
        LOGGER.debug("Processing update user roles for request - {}", request);
        final UserUpdateRolesResponse userUpdateRolesResponse = userRoleServiceFacade.updateUserOrganizationClientsRoles(request);
        LOGGER.debug("Successfully proceeded update user roles for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(userUpdateRolesResponse);
    }
}
