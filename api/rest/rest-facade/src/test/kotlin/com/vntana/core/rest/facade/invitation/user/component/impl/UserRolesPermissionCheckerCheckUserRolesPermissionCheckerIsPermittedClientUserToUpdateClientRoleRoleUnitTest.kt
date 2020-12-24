package com.vntana.core.rest.facade.invitation.user.component.impl

import com.vntana.core.model.auth.response.UserRoleModel
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 23.12.2020
 * Time: 18:45
 */
class UserRolesPermissionCheckerCheckUserRolesPermissionCheckerIsPermittedClientUserToUpdateClientRoleRoleUnitTest : AbstractUserRolesPermissionsCheckerComponentUnitTest() {

    @Test
    fun `test when user has not client roles`() {
        resetAll()
        val clientRoles = listOf(userRoleRestTestHelper.buildUpdateClientRoleRequest(userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER))
        val request = userRoleRestTestHelper.buildUserUpdateOrganizationClientRoleRequest(updateClientRoles = clientRoles)
        expect(userRoleService.findByClientOrganizationAndUser(request.updateClientRoles[0].clientUuid, request.uuid)).andReturn(Optional.empty())
        replayAll()
        assertThat(checkerComponent.isPermittedClientUserToUpdateClientRole(request)).isFalse()
        verifyAll()
    }
    
    @Test
    fun `test when CLIENT_ADMIN updates`() {
        resetAll()
        val clientRoles = listOf(userRoleRestTestHelper.buildUpdateClientRoleRequest(userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER))
        val request = userRoleRestTestHelper.buildUserUpdateOrganizationClientRoleRequest(updateClientRoles = clientRoles)
        val clientAdminRole = userRoleCommonTestHelper.buildUserClientAdminRole()
        expect(userRoleService.findByClientOrganizationAndUser(request.updateClientRoles[0].clientUuid, request.uuid)).andReturn(Optional.of(clientAdminRole))
        expect(userRoleService.findByClientOrganizationAndUser(request.updateClientRoles[0].clientUuid, request.requestedUserUuid)).andReturn(Optional.empty())
        replayAll()
        assertThat(checkerComponent.isPermittedClientUserToUpdateClientRole(request)).isTrue()
        verifyAll()
    }
    
    @Test
    fun `test when CLIENT_CONTENT_MANAGER grants CLIENT_ADMIN`() {
        resetAll()
        val clientRoles = listOf(userRoleRestTestHelper.buildUpdateClientRoleRequest(userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_ADMIN))
        val request = userRoleRestTestHelper.buildUserUpdateOrganizationClientRoleRequest(updateClientRoles = clientRoles)
        val clientContentManagerRole = userRoleCommonTestHelper.buildUserClientContentManagerRole()
        expect(userRoleService.findByClientOrganizationAndUser(request.updateClientRoles[0].clientUuid, request.uuid)).andReturn(Optional.of(clientContentManagerRole))
        expect(userRoleService.findByClientOrganizationAndUser(request.updateClientRoles[0].clientUuid, request.requestedUserUuid)).andReturn(Optional.empty())
        replayAll()
        assertThat(checkerComponent.isPermittedClientUserToUpdateClientRole(request)).isFalse()
        verifyAll()
    }

    @Test
    fun `test when CLIENT_VIEWER grants CLIENT_CONTENT_MANAGER role`() {
        resetAll()
        val clientRoles = listOf(userRoleRestTestHelper.buildUpdateClientRoleRequest(userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER))
        val request = userRoleRestTestHelper.buildUserUpdateOrganizationClientRoleRequest(updateClientRoles = clientRoles)
        val clientViewerRole = userRoleCommonTestHelper.buildUserClientViewerRole()
        expect(userRoleService.findByClientOrganizationAndUser(request.updateClientRoles[0].clientUuid, request.uuid)).andReturn(Optional.of(clientViewerRole))
        expect(userRoleService.findByClientOrganizationAndUser(request.updateClientRoles[0].clientUuid, request.requestedUserUuid)).andReturn(Optional.empty())
        replayAll()
        assertThat(checkerComponent.isPermittedClientUserToUpdateClientRole(request)).isFalse()
        verifyAll()
    }
    
    @Test
    fun `test when CLIENT_CONTENT_MANAGER updates existed CLIENT_ADMIN role`() {
        resetAll()
        val clientRoles = listOf(userRoleRestTestHelper.buildUpdateClientRoleRequest(userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_VIEWER))
        val request = userRoleRestTestHelper.buildUserUpdateOrganizationClientRoleRequest(updateClientRoles = clientRoles)
        val clientContentManagerRole = userRoleCommonTestHelper.buildUserClientContentManagerRole()
        val clientAdminRole = userRoleCommonTestHelper.buildUserClientAdminRole()
        expect(userRoleService.findByClientOrganizationAndUser(request.updateClientRoles[0].clientUuid, request.uuid)).andReturn(Optional.of(clientContentManagerRole))
        expect(userRoleService.findByClientOrganizationAndUser(request.updateClientRoles[0].clientUuid, request.requestedUserUuid)).andReturn(Optional.of(clientAdminRole))
        replayAll()
        assertThat(checkerComponent.isPermittedClientUserToUpdateClientRole(request)).isFalse()
        verifyAll()
    }
    
    @Test
    fun `test when CLIENT_ADMIN updates existed roles`() {
        resetAll()
        val clientRoles = listOf(
                userRoleRestTestHelper.buildUpdateClientRoleRequest(userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_ADMIN),
                userRoleRestTestHelper.buildUpdateClientRoleRequest(userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_VIEWER)
        )
        val request = userRoleRestTestHelper.buildUserUpdateOrganizationClientRoleRequest(updateClientRoles = clientRoles)
        val clientRole1 = userRoleCommonTestHelper.buildUserClientContentManagerRole()
        val clientRole2 = userRoleCommonTestHelper.buildUserClientAdminRole()
        val clientAdminRole = userRoleCommonTestHelper.buildUserClientAdminRole()
        expect(userRoleService.findByClientOrganizationAndUser(request.updateClientRoles[0].clientUuid, request.uuid)).andReturn(Optional.of(clientAdminRole))
        expect(userRoleService.findByClientOrganizationAndUser(request.updateClientRoles[0].clientUuid, request.requestedUserUuid)).andReturn(Optional.of(clientRole1))
        expect(userRoleService.findByClientOrganizationAndUser(request.updateClientRoles[1].clientUuid, request.uuid)).andReturn(Optional.of(clientAdminRole))
        expect(userRoleService.findByClientOrganizationAndUser(request.updateClientRoles[1].clientUuid, request.requestedUserUuid)).andReturn(Optional.of(clientRole2))
        replayAll()
        assertThat(checkerComponent.isPermittedClientUserToUpdateClientRole(request)).isTrue()
        verifyAll()
    }
}