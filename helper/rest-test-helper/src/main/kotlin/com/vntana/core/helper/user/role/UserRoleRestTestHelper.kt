package com.vntana.core.helper.user.role

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.role.request.*

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 11:58 AM
 */
open class UserRoleRestTestHelper : AbstractRestTestHelper() {

    fun buildUserRoleGrantSuperAdminRequest(userUuid: String? = uuid()): UserRoleGrantSuperAdminRequest = UserRoleGrantSuperAdminRequest(userUuid)

    fun buildUserRoleRevokeOrganizationAdminRequest(
            organizationUuid: String? = uuid(),
            userUuid: String? = uuid()
    ): UserRoleRevokeOrganizationAdminRequest = UserRoleRevokeOrganizationAdminRequest(userUuid, organizationUuid)

    fun buildUserRoleGrantOrganizationAdminRequest(
            organizationUuid: String? = uuid(),
            userUuid: String? = uuid()
    ): UserRoleGrantOrganizationAdminRequest = UserRoleGrantOrganizationAdminRequest(userUuid, organizationUuid)

    fun buildUserRoleGrantClientRequest(
            userUuid: String? = uuid(),
            clientUuid: String? = uuid(),
            userRole: UserRoleModel? = UserRoleModel.CLIENT_ORGANIZATION_ADMIN
    ): UserRoleGrantClientOrganizationRequest = UserRoleGrantClientOrganizationRequest(userUuid, clientUuid, userRole)

    fun buildUserRoleRevokeClientRequest(
            userUuid: String? = uuid(),
            clientUuid: String? = uuid(),
            userRole: UserRoleModel? = UserRoleModel.CLIENT_ORGANIZATION_ADMIN
    ): UserRoleRevokeClientRequest = UserRoleRevokeClientRequest(userUuid, clientUuid, userRole)

    fun buildUserRoleRevokeOrganizationClientsRequest(
            userUuid: String? = uuid(),
            organizationUuid: String? = uuid()
    ): UserRoleRevokeOrganizationClientsRequest = UserRoleRevokeOrganizationClientsRequest(userUuid, organizationUuid)
    
    fun buildUserUpdateOrganizationRoleRequest(
            userUuid: String? = uuid(),
            requestedUserUuid: String? = uuid(),
            organizationUuid: String? = uuid()
    ): UserUpdateOrganizationRoleRequest = UserUpdateOrganizationRoleRequest(userUuid, organizationUuid, requestedUserUuid)
    
    fun buildUserUpdateOrganizationClientRoleRequest(
            userUuid: String? = uuid(),
            requestedUserUuid: String? = uuid(),
            organizationUuid: String? = uuid(),
            updateClientRoles: List<UpdateClientRoleRequest>? = listOf(buildUpdateClientRoleRequest(), buildUpdateClientRoleRequest())
    ): UserUpdateOrganizationClientsRolesRequest = UserUpdateOrganizationClientsRolesRequest(userUuid, organizationUuid, requestedUserUuid, updateClientRoles)
    
    fun buildUpdateClientRoleRequest(
            clientUuid: String? = uuid(),
            userRoleModel: UserRoleModel? = UserRoleModel.CLIENT_ORGANIZATION_ADMIN
    ): UpdateClientRoleRequest = UpdateClientRoleRequest(clientUuid, userRoleModel)
    
    fun buildUpdatedClientRoleModel(
            clientUuid: String? = uuid(),
            revokeUserRoleModel: UserRoleModel? = UserRoleModel.CLIENT_ORGANIZATION_ADMIN,
            grantUserRoleModel: UserRoleModel? = UserRoleModel.CLIENT_ORGANIZATION_VIEWER
    ): UpdatedClientRoleRequestModel = UpdatedClientRoleRequestModel(clientUuid, revokeUserRoleModel, grantUserRoleModel)
}