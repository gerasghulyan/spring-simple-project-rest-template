package com.vntana.core.rest.client.user.role;

import com.vntana.core.model.user.role.request.UserRoleGrantClientAdminOrganizationRequest;
import com.vntana.core.model.user.role.request.UserRoleGrantOrganizationAdminRequest;
import com.vntana.core.model.user.role.request.UserRoleGrantSuperAdminRequest;
import com.vntana.core.model.user.role.request.UserRoleRevokeOrganizationAdminRequest;
import com.vntana.core.model.user.role.response.UserRoleGrantClientAdminResponse;
import com.vntana.core.model.user.role.response.UserRoleGrantOrganizationAdminResponse;
import com.vntana.core.model.user.role.response.UserRoleGrantSuperAdminResponse;
import com.vntana.core.model.user.role.response.UserRoleRevokeOrganizationAdminResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 3:18 PM
 */
@FeignClient(name = "coreUserRoles", path = "user-roles", url = "${ms.core.url}", primary = false)
public interface UserRoleResourceClient {

    @PostMapping("super-admin")
    ResponseEntity<UserRoleGrantSuperAdminResponse> grantSuperAdmin(@RequestBody final UserRoleGrantSuperAdminRequest request);

    @PostMapping("organization-admin")
    ResponseEntity<UserRoleGrantOrganizationAdminResponse> grantUserOrganizationAdminRole(@RequestBody final UserRoleGrantOrganizationAdminRequest request);

    @DeleteMapping("organization-admin")
    ResponseEntity<UserRoleRevokeOrganizationAdminResponse> revokeUserOrganizationAdminRole(@RequestBody final UserRoleRevokeOrganizationAdminRequest request);

    @PostMapping("client-admin")
    ResponseEntity<UserRoleGrantClientAdminResponse> grantUserClientAdminRole(@RequestBody final UserRoleGrantClientAdminOrganizationRequest request);
}
