package com.vntana.core.rest.facade.user.role.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel
import com.vntana.core.rest.facade.user.role.AbstractUserRoleServiceFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 15.12.2020
 * Time: 18:05
 */
class UserRoleUpdateUserOrganizationRoleFacadeUnitTest : AbstractUserRoleServiceFacadeUnitTest() {

    @Test
    fun `test when precondition failed`() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationRoleRequest()
        val error = SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, UserRoleErrorResponseModel.USER_NOT_FOUND)
        expect(preconditionChecker.checkUpdateUserOrganizationRoles(request)).andReturn(error)
        replayAll()
        assertBasicErrorResultResponse(userRoleServiceFacade.updateUserOrganizationRole(request), error.error)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildUserUpdateOrganizationRoleRequest()
        val client1 = commonTestHelper.buildUserClientAdminRole()
        val client2 = commonTestHelper.buildUserClientContentManagerRole()
        val client3 = commonTestHelper.buildUserClientViewerRole()
        val clients = listOf(client1, client2, client3)
        val clientsUuids = listOf(client1.clientOrganization.uuid, client2.clientOrganization.uuid, client3.clientOrganization.uuid)
        val revokeClientsRolesDto = commonTestHelper.buildUserRevokeClientsRolesDto(userUuid = request.requestedUserUuid, clientUuids = clientsUuids)
        val grantOrganizationAdminRoleDto = commonTestHelper.buildUserGrantOrganizationRoleDto(userUuid = request.requestedUserUuid, organizationUuid = request.organizationUuid)
        val adminRole = commonTestHelper.buildUserOrganizationAdminRole()
        expect(preconditionChecker.checkUpdateUserOrganizationRoles(request)).andReturn(SingleErrorWithStatus.empty())
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.requestedUserUuid)).andReturn(clients)
        expect(userRoleService.revokeUserClientsRoles(revokeClientsRolesDto))
        expect(userRoleService.grantOrganizationAdminRole(grantOrganizationAdminRoleDto)).andReturn(adminRole)
        replayAll()
        userRoleServiceFacade.updateUserOrganizationRole(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userUuid).isEqualTo(request.requestedUserUuid)
        }
        verifyAll()
    }
}