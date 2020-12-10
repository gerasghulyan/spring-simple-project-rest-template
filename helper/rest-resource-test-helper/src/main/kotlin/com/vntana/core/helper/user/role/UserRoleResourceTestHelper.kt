package com.vntana.core.helper.user.role

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.role.response.UserRoleGrantOrganizationAdminResponse
import com.vntana.core.model.user.role.response.UserRoleGrantSuperAdminResponse
import com.vntana.core.model.user.role.response.UserRoleRevokeOrganizationAdminResponse
import com.vntana.core.rest.client.user.role.UserRoleResourceClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 3:29 PM
 */
@Component
class UserRoleResourceTestHelper : UserRoleRestTestHelper() {

    @Autowired
    protected lateinit var userRoleResourceClient: UserRoleResourceClient

    fun grantUserClientRole(
            userUuid: String? = uuid(),
            clientUuid: String? = uuid(),
            userRole: UserRoleModel? = UserRoleModel.CLIENT_ORGANIZATION_ADMIN) {
        userRoleResourceClient.grantUserClientRole(buildUserRoleGrantClientRequest(userUuid, clientUuid, userRole))
    }

    fun revokeClientRole(
            userUuid: String? = uuid(),
            clientUuid: String? = uuid(),
            userRole: UserRoleModel? = UserRoleModel.CLIENT_ORGANIZATION_ADMIN) {
        userRoleResourceClient.revokeUserClientRole(buildUserRoleRevokeClientRequest(userUuid, clientUuid, userRole))
    }

    fun grantSuperAdmin(userUuid: String? = uuid()): ResponseEntity<UserRoleGrantSuperAdminResponse> = userRoleResourceClient.grantSuperAdmin(buildUserRoleGrantSuperAdminRequest(userUuid))

    fun grantUserOrganizationAdminRole(
            userUuid: String? = uuid(),
            organizationUuid: String? = uuid()
    ): ResponseEntity<UserRoleGrantOrganizationAdminResponse> = userRoleResourceClient.grantUserOrganizationAdminRole(buildUserRoleGrantOrganizationAdminRequest(userUuid = userUuid, organizationUuid = organizationUuid))

    fun revokeUserOrganizationAdminRole(
            userUuid: String? = uuid(),
            organizationUuid: String? = uuid()
    ): ResponseEntity<UserRoleRevokeOrganizationAdminResponse> = userRoleResourceClient.revokeUserOrganizationAdminRole(buildUserRoleRevokeOrganizationAdminRequest(userUuid = userUuid, organizationUuid = organizationUuid))
}