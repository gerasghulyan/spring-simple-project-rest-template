package com.vntana.core.rest.facade.user.role.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.domain.client.ClientOrganization
import com.vntana.core.domain.user.AbstractClientOrganizationAwareUserRole
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel
import com.vntana.core.rest.facade.user.role.AbstractUserRoleServiceFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 12/9/2020
 * Time: 4:57 PM
 */
class UserRoleRevokeOrganizationClientsRolesFacadeUnitTest : AbstractUserRoleServiceFacadeUnitTest() {

    @Test
    fun `test when precondition failed`() {
        resetAll()
        val request = restTestHelper.buildUserRoleRevokeOrganizationClientsRequest()
        val error = SingleErrorWithStatus.of(HttpStatus.SC_NOT_FOUND, UserRoleErrorResponseModel.USER_NOT_FOUND)
        expect(preconditionChecker.checkRevokeOrganizationClientsRoles(request)).andReturn(error)
        replayAll()
        assertBasicErrorResultResponse(userRoleServiceFacade.revokeOrganizationClientsRoles(request), error.error)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildUserRoleRevokeOrganizationClientsRequest()
        val clients = listOf(commonTestHelper.buildUserClientAdminRole(), commonTestHelper.buildUserClientContentManagerRole(), commonTestHelper.buildUserClientViewerRole())
        val dto = commonTestHelper.buildUserRevokeClientsRolesDto(
                userUuid = request.userUuid,
                clientUuids = clients.map(AbstractClientOrganizationAwareUserRole::getClientOrganization).map(ClientOrganization::getUuid).toList()
        )
        expect(preconditionChecker.checkRevokeOrganizationClientsRoles(request)).andReturn(SingleErrorWithStatus.empty())
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(clients)
        expect(userRoleService.revokeUserClientsRoles(dto)).andVoid()
        expect(tokenAuthenticationService.expireAllByUser(request.userUuid)).andVoid()
        replayAll()
        userRoleServiceFacade.revokeOrganizationClientsRoles(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userUuid).isEqualTo(request.userUuid)
        }
        verifyAll()
    }
}