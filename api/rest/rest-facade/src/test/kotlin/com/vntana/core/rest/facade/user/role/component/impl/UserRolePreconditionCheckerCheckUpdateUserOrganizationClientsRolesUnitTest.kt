package com.vntana.core.rest.facade.user.role.component.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel
import com.vntana.core.rest.facade.user.role.component.AbstractUserRoleFacadePreconditionCheckerComponentUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Vardan Aivazian
 * Date: 15.12.2020
 * Time: 18:20
 */
class UserRolePreconditionCheckerCheckUpdateUserOrganizationClientsRolesUnitTest : AbstractUserRoleFacadePreconditionCheckerComponentUnitTest() {

    @Test
    fun `test when organization not found`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationClientRoleRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkUpdateUserOrganizationClientsRoles(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.ORGANIZATION_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when user not found`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationClientRoleRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkUpdateUserOrganizationClientsRoles(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.USER_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }
    
    @Test
    fun `test when granted to organization owner`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationClientRoleRequest()
        val organizationOwnerRole = commonTestHelper.buildUserOrganizationOwnerRole()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(Optional.of(organizationOwnerRole))
        replayAll()
        preconditionChecker.checkUpdateUserOrganizationClientsRoles(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.REQUESTED_USER_IS_ORGANIZATION_OWNER)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
        }
        verifyAll()
    }
    
    @Test
    fun `test when granted client not found`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationClientRoleRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(Optional.empty())
        expect(organizationClientService.existsByUuid(request.updateClientRoles[0].clientUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkUpdateUserOrganizationClientsRoles(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.CLIENT_ORGANIZATION_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }
    
    @Test
    fun `test when CLIENT_ORGANIZATION_CONTENT_MANAGER grants CLIENT_ORGANIZATION_ADMIN`() {
        resetAll()
        val clientRoleRequest = restTestHelper.buildUpdateClientRoleRequest(userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_ADMIN)
        val request = restTestHelper.buildUserUpdateOrganizationClientRoleRequest(updateClientRoles = listOf(clientRoleRequest))
        val contentManagerRole = commonTestHelper.buildUserClientContentManagerRole()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(Optional.empty())
        expect(organizationClientService.existsByUuid(request.updateClientRoles[0].clientUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.authorizedUserUuid)).andReturn(Optional.empty())
        expect(userRoleService.findByClientOrganizationAndUser(request.updateClientRoles[0].clientUuid, request.authorizedUserUuid)).andReturn(Optional.of(contentManagerRole))
        expect(userRolesPermissionsCheckerComponent.isPermittedToGrant(UserRoleModel.valueOf(contentManagerRole.userRole.name), clientRoleRequest.clientRole)).andReturn(false)
        replayAll()
        preconditionChecker.checkUpdateUserOrganizationClientsRoles(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.USER_HAS_NOT_PERMISSION_GRANT_CLIENT_ROLE)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_FORBIDDEN)
        }
        verifyAll()
    }

    @Test
    fun `test when ORGANIZATION_OWNER grants CLIENT_ORGANIZATION_VIEWER role to existed ORGANIZATION_ADMIN`() {
        resetAll()
        val clientRoleRequest = restTestHelper.buildUpdateClientRoleRequest(userRoleModel = UserRoleModel.CLIENT_ORGANIZATION_VIEWER)
        val request = restTestHelper.buildUserUpdateOrganizationClientRoleRequest(updateClientRoles = listOf(clientRoleRequest))
        val organizationOwnerRole = commonTestHelper.buildUserOrganizationOwnerRole()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(Optional.empty())
        expect(organizationClientService.existsByUuid(request.updateClientRoles[0].clientUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.authorizedUserUuid)).andReturn(Optional.of(organizationOwnerRole))
        replayAll()
        assertThat(preconditionChecker.checkUpdateUserOrganizationClientsRoles(request).isPresent).isEqualTo(false)
        verifyAll()
    }
}