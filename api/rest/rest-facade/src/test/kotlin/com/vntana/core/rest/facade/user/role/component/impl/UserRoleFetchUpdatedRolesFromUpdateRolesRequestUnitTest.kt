package com.vntana.core.rest.facade.user.role.component.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.rest.facade.user.role.component.AbstractUserRoleFacadeActionItemRetrieverComponentUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 17.12.2020
 * Time: 19:58
 */
class UserRoleFetchUpdatedRolesFromUpdateRolesRequestUnitTest : AbstractUserRoleFacadeActionItemRetrieverComponentUnitTest() {

    @Test
    fun `test when updated existed client roles`() {
        resetAll()
        val clientOrganization1 = clientOrganizationCommonTestHelper.buildClientOrganization()
        val clientOrganization2 = clientOrganizationCommonTestHelper.buildClientOrganization()
        val clientRole1 = commonTestHelper.buildUserClientContentManagerRole(clientOrganization = clientOrganization1)
        val clientRole2 = commonTestHelper.buildUserClientViewerRole(clientOrganization = clientOrganization2)
        val existedClientRole1 = commonTestHelper.buildUserClientViewerRole(clientOrganization = clientOrganization1)
        val existedClientRole2 = commonTestHelper.buildUserClientAdminRole(clientOrganization = clientOrganization2)
        val existedClientRoles = listOf(existedClientRole1, existedClientRole2)
        val organizationAdminRole = commonTestHelper.buildUserOrganizationAdminRole()
        val updateClientRole1 = restTestHelper.buildUpdatedClientRoleModel(clientUuid = clientRole1.clientOrganization.uuid, revokeUserRoleModel = UserRoleModel.valueOf(existedClientRole1.userRole.name), grantUserRoleModel = UserRoleModel.valueOf(clientRole1.userRole.name))
        val updateClientRole2 = restTestHelper.buildUpdatedClientRoleModel(clientUuid = clientRole2.clientOrganization.uuid, revokeUserRoleModel = UserRoleModel.valueOf(existedClientRole2.userRole.name), grantUserRoleModel =  UserRoleModel.valueOf(clientRole2.userRole.name))
        val updateClientRoleRequest1 = restTestHelper.buildUpdateClientRoleRequest(clientUuid = clientRole1.clientOrganization.uuid, userRoleModel = UserRoleModel.valueOf(clientRole1.userRole.name))
        val updateClientRoleRequest2 = restTestHelper.buildUpdateClientRoleRequest(clientUuid = clientRole2.clientOrganization.uuid, userRoleModel = UserRoleModel.valueOf(clientRole2.userRole.name))
        val updatedRoles = listOf(updateClientRole1, updateClientRole2)
        val updateRolesRequest = listOf(updateClientRoleRequest1, updateClientRoleRequest2)
        val request = restTestHelper.buildUserUpdateOrganizationClientRoleRequest(updateClientRoles = updateRolesRequest)
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.requestedUserUuid)).andReturn(existedClientRoles)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.uuid)).andReturn(Optional.of(organizationAdminRole))
        replayAll()
        userRoleActionItemRetrieverComponent.fetchUpdatedRolesFromUpdateRolesRequest(request).let {
            assertThat(it).containsExactlyInAnyOrderElementsOf(updatedRoles)
        }
        verifyAll()
    }
    
    @Test
    fun `test when not found roles for update`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationClientRoleRequest()
        val existedClientRole1 = commonTestHelper.buildUserClientViewerRole()
        val existedClientRole2 = commonTestHelper.buildUserClientAdminRole()
        val existedClientRoles = listOf(existedClientRole1, existedClientRole2)
        val organizationAdminRole = commonTestHelper.buildUserOrganizationAdminRole()
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.requestedUserUuid)).andReturn(existedClientRoles)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.uuid)).andReturn(Optional.of(organizationAdminRole))
        replayAll()
        userRoleActionItemRetrieverComponent.fetchUpdatedRolesFromUpdateRolesRequest(request).let {
            assertThat(it).isEmpty()
        }
        verifyAll()
    }    
}