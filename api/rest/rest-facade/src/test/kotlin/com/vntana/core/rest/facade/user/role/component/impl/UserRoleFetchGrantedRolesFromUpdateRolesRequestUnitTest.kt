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
 * Time: 18:13
 */
class UserRoleFetchGrantedRolesFromUpdateRolesRequestUnitTest : AbstractUserRoleFacadeActionItemRetrieverComponentUnitTest() {

    @Test
    fun `test when organization admin grants all requested client roles, not existed roles`() {
        resetAll()
        val clientRole1 = commonTestHelper.buildUserClientContentManagerRole()
        val clientRole2 = commonTestHelper.buildUserClientContentManagerRole()
        val updateClientRole1 = restTestHelper.buildUpdateClientRoleRequest(clientUuid = clientRole1.clientOrganization.uuid, userRoleModel = UserRoleModel.valueOf(clientRole1.userRole.name))
        val updateClientRole2 = restTestHelper.buildUpdateClientRoleRequest(clientUuid = clientRole2.clientOrganization.uuid, userRoleModel = UserRoleModel.valueOf(clientRole2.userRole.name))
        val request = restTestHelper.buildUserUpdateOrganizationClientRoleRequest(updateClientRoles = listOf(updateClientRole1, updateClientRole2))
        val organizationAdminRole = commonTestHelper.buildUserOrganizationAdminRole()
        val grantRoles = listOf(updateClientRole1, updateClientRole2)
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.requestedUserUuid)).andReturn(listOf())
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.uuid)).andReturn(Optional.of(organizationAdminRole))
        replayAll()
        userRoleActionItemRetrieverComponent.fetchGrantedRolesFromUpdateRolesRequest(request).let {
            assertThat(it).containsExactlyInAnyOrderElementsOf(grantRoles)
        }
        verifyAll()
    }

    @Test
    fun `test when granted new roles and changed another`() {
        resetAll()
        val clientRole1 = commonTestHelper.buildUserClientContentManagerRole()
        val clientRole2 = commonTestHelper.buildUserClientContentManagerRole()
        val clientRole3 = commonTestHelper.buildUserClientAdminRole()
        val updateClientRole1 = restTestHelper.buildUpdateClientRoleRequest(clientUuid = clientRole1.clientOrganization.uuid, userRoleModel = UserRoleModel.valueOf(clientRole1.userRole.name))
        val updateClientRole2 = restTestHelper.buildUpdateClientRoleRequest(clientUuid = clientRole2.clientOrganization.uuid, userRoleModel = UserRoleModel.valueOf(clientRole2.userRole.name))
        val existedClientRoles = listOf(clientRole1, clientRole3)
        val updateClientRoles = listOf(updateClientRole1, updateClientRole2)
        val request = restTestHelper.buildUserUpdateOrganizationClientRoleRequest(updateClientRoles = updateClientRoles)
        val organizationAdminRole = commonTestHelper.buildUserOrganizationAdminRole()
        val grantRoles = listOf(updateClientRole2)
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.requestedUserUuid)).andReturn(existedClientRoles)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.uuid)).andReturn(Optional.of(organizationAdminRole))
        replayAll()
        userRoleActionItemRetrieverComponent.fetchGrantedRolesFromUpdateRolesRequest(request).let {
            assertThat(it).containsExactlyInAnyOrderElementsOf(grantRoles)
        }
        verifyAll()
    }


    @Test
    fun `test granted when one untouched and one revoked`() {
        resetAll()
        val clientRole1 = commonTestHelper.buildUserClientContentManagerRole()
        val clientRole2 = commonTestHelper.buildUserClientContentManagerRole()
        val clientRole3 = commonTestHelper.buildUserClientAdminRole()
        val updateClientRole1 = restTestHelper.buildUpdateClientRoleRequest(clientUuid = clientRole1.clientOrganization.uuid, userRoleModel = UserRoleModel.valueOf(clientRole1.userRole.name))
        val updateClientRole2 = restTestHelper.buildUpdateClientRoleRequest(clientUuid = clientRole2.clientOrganization.uuid, userRoleModel = UserRoleModel.valueOf(clientRole2.userRole.name))
        val existedClientRoles = listOf(clientRole1, clientRole3)
        val updateClientRoles = listOf(updateClientRole1, updateClientRole2)
        val request = restTestHelper.buildUserUpdateOrganizationClientRoleRequest(updateClientRoles = updateClientRoles)
        val organizationAdminRole = commonTestHelper.buildUserOrganizationAdminRole()
        val grantedRoles = listOf(updateClientRole2)
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.requestedUserUuid)).andReturn(existedClientRoles)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.uuid)).andReturn(Optional.of(organizationAdminRole))
        replayAll()
        userRoleActionItemRetrieverComponent.fetchGrantedRolesFromUpdateRolesRequest(request).let {
            assertThat(it).containsExactlyInAnyOrderElementsOf(grantedRoles)
        }
        verifyAll()
    }
}