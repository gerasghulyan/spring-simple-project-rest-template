package com.vntana.core.rest.facade.user.role.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel
import com.vntana.core.rest.facade.user.role.AbstractUserRoleServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 4:47 PM
 */
class UserRoleGrantOrganizationAdminRoleFacadeUnitTest : AbstractUserRoleServiceFacadeUnitTest() {

    @Test
    fun `test when precondition failed`() {
        resetAll()
        val request = restTestHelper.buildUserRoleGrantOrganizationAdminRequest()
        val error = SingleErrorWithStatus.of(404, UserRoleErrorResponseModel.ORGANIZATION_NOT_FOUND)
        expect(preconditionChecker.checkGrantOrganizationAdminRole(request)).andReturn(error)
        replayAll()
        assertBasicErrorResultResponse(userRoleServiceFacade.grantOrganizationAdminRole(request), error.error)
        verifyAll()
    }

    @Test
    fun `test without client roles`() {
        resetAll()
        val request = restTestHelper.buildUserRoleGrantOrganizationAdminRequest()
        val dto = commonTestHelper.buildUserGrantOrganizationRoleDto(
                userUuid = request.userUuid,
                organizationUuid = request.organizationUuid
        )
        val adminRole = commonTestHelper.buildUserOrganizationAdminRole()
        expect(preconditionChecker.checkGrantOrganizationAdminRole(request)).andReturn(SingleErrorWithStatus.empty())
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(emptyList())
        expect(userRoleService.grantOrganizationAdminRole(dto)).andReturn(adminRole)
        replayAll()
        userRoleServiceFacade.grantOrganizationAdminRole(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userUuid).isEqualTo(adminRole.user.uuid)
        }
        verifyAll()
    }

    @Test
    fun `test with client roles`() {
        resetAll()
        val request = restTestHelper.buildUserRoleGrantOrganizationAdminRequest()
        val dto = commonTestHelper.buildUserGrantOrganizationRoleDto(
                userUuid = request.userUuid,
                organizationUuid = request.organizationUuid
        )
        val adminRole = commonTestHelper.buildUserOrganizationAdminRole()
        val clientAdminRole = commonTestHelper.buildUserClientAdminRole()
        val clientContentManagerRole = commonTestHelper.buildUserClientContentManagerRole()
        val revokeClientsRolesDto = commonTestHelper.buildUserRevokeClientsRolesDto(
                userUuid = dto.userUuid,
                clientUuids = listOf(clientAdminRole.clientOrganization.uuid, clientContentManagerRole.clientOrganization.uuid)
        )
        expect(preconditionChecker.checkGrantOrganizationAdminRole(request)).andReturn(SingleErrorWithStatus.empty())
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(listOf(clientAdminRole, clientContentManagerRole))
        expect(userRoleService.grantOrganizationAdminRole(dto)).andReturn(adminRole)
        expect(userRoleService.revokeUserClientsRoles(revokeClientsRolesDto))
        expect(tokenAuthenticationService.expireAllByUser(revokeClientsRolesDto.userUuid)).andVoid()
        replayAll()
        userRoleServiceFacade.grantOrganizationAdminRole(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userUuid).isEqualTo(adminRole.user.uuid)
        }
        verifyAll()
    }
}