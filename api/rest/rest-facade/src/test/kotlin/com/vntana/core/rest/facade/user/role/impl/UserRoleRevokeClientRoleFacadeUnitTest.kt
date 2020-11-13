package com.vntana.core.rest.facade.user.role.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.domain.user.UserRole
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel
import com.vntana.core.rest.facade.user.role.AbstractUserRoleServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 11/12/2020
 * Time: 3:44 PM
 */
class UserRoleRevokeClientRoleFacadeUnitTest : AbstractUserRoleServiceFacadeUnitTest() {

    @Test
    fun `test when precondition failed`() {
        resetAll()
        val request = restTestHelper.buildUserRoleRevokeClientRequest()
        val error = SingleErrorWithStatus.of(404, UserRoleErrorResponseModel.CLIENT_ORGANIZATION_NOT_FOUND)
        expect(preconditionChecker.checkRevokeClientRole(request)).andReturn(error)
        replayAll()
        assertBasicErrorResultResponse(userRoleServiceFacade.revokeClientRole(request), error.error)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildUserRoleRevokeClientRequest()
        val dto = commonTestHelper.buildUserRevokeClientRoleDto(
                userUuid = request.userUuid,
                clientOrganizationUuid = request.clientUuid,
                clientRole = UserRole.valueOf(request.userRole.name)
        )
        expect(preconditionChecker.checkRevokeClientRole(request)).andReturn(SingleErrorWithStatus.empty())
        expect(userRoleService.revokeClientRole(dto))
        expect(tokenAuthenticationService.expireAllByUser(request.userUuid))
        replayAll()
        userRoleServiceFacade.revokeClientRole(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userUuid).isEqualTo(request.userUuid)
        }
        verifyAll()
    }
}