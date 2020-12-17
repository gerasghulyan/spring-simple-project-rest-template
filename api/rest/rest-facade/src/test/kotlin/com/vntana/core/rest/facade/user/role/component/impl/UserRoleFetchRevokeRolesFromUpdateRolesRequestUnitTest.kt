package com.vntana.core.rest.facade.user.role.component.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.rest.facade.user.role.component.AbstractUserRoleFacadeActionItemRetrieverComponentUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 16.12.2020
 * Time: 16:42
 */
class UserRoleFetchRevokeRolesFromUpdateRolesRequestUnitTest : AbstractUserRoleFacadeActionItemRetrieverComponentUnitTest() {

    @Test
    fun `test when revoke all existed client roles`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationClientRoleRequest()
        val clientRole1 = commonTestHelper.buildUserClientContentManagerRole()
        val clientRole2 = commonTestHelper.buildUserClientContentManagerRole()
        val updateClientRole1 = restTestHelper.buildUpdateClientRoleRequest(clientUuid = clientRole1.clientOrganization.uuid, userRoleModel = UserRoleModel.valueOf(clientRole1.userRole.name))
        val updateClientRole2 = restTestHelper.buildUpdateClientRoleRequest(clientUuid = clientRole2.clientOrganization.uuid, userRoleModel = UserRoleModel.valueOf(clientRole2.userRole.name))
        val existedClientRoles = listOf(clientRole1, clientRole2)
        val organizationAdminRole = commonTestHelper.buildUserOrganizationAdminRole()
        val revokeRoles = listOf(updateClientRole1, updateClientRole2)
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.requestedUserUuid)).andReturn(existedClientRoles)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.uuid)).andReturn(Optional.of(organizationAdminRole))
        replayAll()
        userRoleActionItemRetrieverComponent.fetchRevokeRolesFromUpdateRolesRequest(request).let {
            assertThat(it).containsExactlyInAnyOrderElementsOf(revokeRoles)
        }
        verifyAll()
    }
    
    @Test
    fun `test when revoke two and grant one`() {
        resetAll()
        val clientRole1 = commonTestHelper.buildUserClientContentManagerRole()
        val clientRole2 = commonTestHelper.buildUserClientContentManagerRole()
        val clientRole3 = commonTestHelper.buildUserClientAdminRole()
        val updateClientRole1 = restTestHelper.buildUpdateClientRoleRequest(clientUuid = clientRole1.clientOrganization.uuid, userRoleModel = UserRoleModel.valueOf(clientRole1.userRole.name))
        val updateClientRole2 = restTestHelper.buildUpdateClientRoleRequest(clientUuid = clientRole2.clientOrganization.uuid, userRoleModel = UserRoleModel.valueOf(clientRole2.userRole.name))
        val updateClientRole3 = restTestHelper.buildUpdateClientRoleRequest(clientUuid = clientRole3.clientOrganization.uuid, userRoleModel = UserRoleModel.valueOf(clientRole3.userRole.name))
        val existedClientRoles = listOf(clientRole1, clientRole2)
        val updateClientRoles = listOf(updateClientRole3)
        val request = restTestHelper.buildUserUpdateOrganizationClientRoleRequest(updateClientRoles = updateClientRoles)
        val organizationAdminRole = commonTestHelper.buildUserOrganizationAdminRole()
        val revokeRoles = listOf(updateClientRole1, updateClientRole2)
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.requestedUserUuid)).andReturn(existedClientRoles)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.uuid)).andReturn(Optional.of(organizationAdminRole))
        replayAll()
        userRoleActionItemRetrieverComponent.fetchRevokeRolesFromUpdateRolesRequest(request).let {
            assertThat(it).containsExactlyInAnyOrderElementsOf(revokeRoles)
        }
        verifyAll()
    }
    
    @Test
    fun `test when authorized user has client role but not in all user clients`() {
        resetAll()
        val clientRole1 = commonTestHelper.buildUserClientContentManagerRole()
        val clientRole2 = commonTestHelper.buildUserClientContentManagerRole()
        val clientRole3 = commonTestHelper.buildUserClientAdminRole()
        val updateClientRole1 = restTestHelper.buildUpdateClientRoleRequest(clientUuid = clientRole1.clientOrganization.uuid, userRoleModel = UserRoleModel.valueOf(clientRole1.userRole.name))
        val updateClientRole3 = restTestHelper.buildUpdateClientRoleRequest(clientUuid = clientRole3.clientOrganization.uuid, userRoleModel = UserRoleModel.valueOf(clientRole3.userRole.name))
        val existedClientRoles = listOf(clientRole1, clientRole2)
        val authorizedUsersClientRoles = listOf(clientRole1, clientRole3)
        val updateClientRoles = listOf(updateClientRole3)
        val request = restTestHelper.buildUserUpdateOrganizationClientRoleRequest(updateClientRoles = updateClientRoles)
        val revokeRoles = listOf(updateClientRole1)
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.requestedUserUuid)).andReturn(existedClientRoles)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.uuid)).andReturn(Optional.empty())
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.uuid)).andReturn(authorizedUsersClientRoles)
        replayAll()
        userRoleActionItemRetrieverComponent.fetchRevokeRolesFromUpdateRolesRequest(request).let {
            assertThat(it).containsExactlyInAnyOrderElementsOf(revokeRoles)
        }
        verifyAll()
    }
}