package com.vntana.core.rest.resource.user.role;

import com.vntana.commons.web.utils.ResponseEntityUtils;
import com.vntana.core.model.user.role.request.UserRoleGrantOrganizationAdminRequest;
import com.vntana.core.model.user.role.request.UserRoleGrantSuperAdminRequest;
import com.vntana.core.model.user.role.request.UserRoleRevokeOrganizationAdminRequest;
import com.vntana.core.model.user.role.response.UserRoleGrantOrganizationAdminResponse;
import com.vntana.core.model.user.role.response.UserRoleGrantSuperAdminResponse;
import com.vntana.core.model.user.role.response.UserRoleRevokeOrganizationAdminResponse;
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

    @DeleteMapping("organization-admin")
    public ResponseEntity<UserRoleRevokeOrganizationAdminResponse> revokeUserOrganizationAdminRole(@RequestBody final UserRoleRevokeOrganizationAdminRequest request) {
        LOGGER.debug("Processing user-roles resource revokeUserOrganizationAdminRole for request - {}", request);
        final UserRoleRevokeOrganizationAdminResponse userRoleRevokeOrganizationAdminResponse = userRoleServiceFacade.revokeOrganizationAdminRole(request);
        LOGGER.debug("Successfully processing user-roles resource revokeUserOrganizationAdminRole for request - {}", request);
        return ResponseEntityUtils.okWithStatusInHeader(userRoleRevokeOrganizationAdminResponse);
    }
}
