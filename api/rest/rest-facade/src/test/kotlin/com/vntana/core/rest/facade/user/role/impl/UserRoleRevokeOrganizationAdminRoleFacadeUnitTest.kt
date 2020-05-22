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
 * Time: 12:10 PM
 */
class UserRoleRevokeOrganizationAdminRoleFacadeUnitTest : AbstractUserRoleServiceFacadeUnitTest() {

    @Test
    fun `test when precondition failed`() {
        resetAll()
        val request = restTestHelper.buildUserRoleRevokeOrganizationAdminRequest()
        val error = SingleErrorWithStatus.of(404, UserRoleErrorResponseModel.ORGANIZATION_NOT_FOUND)
        expect(preconditionChecker.checkRevokeOrganizationAdminRole(request)).andReturn(error)
        replayAll()
        assertBasicErrorResultResponse(userRoleServiceFacade.revokeOrganizationAdminRole(request), error.error)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildUserRoleRevokeOrganizationAdminRequest()
        val dto = commonTestHelper.buildUserRevokeOrganizationAdminRoleDto(
                userUuid = request.userUuid,
                organizationUuid = request.organizationUuid
        )
        expect(preconditionChecker.checkRevokeOrganizationAdminRole(request)).andReturn(SingleErrorWithStatus.empty())
        expect(userRoleService.revokeOrganizationAdminRole(dto))
        expect(authTokenService.expireAllByUser(request.userUuid))
        replayAll()
        userRoleServiceFacade.revokeOrganizationAdminRole(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userUuid).isEqualTo(request.userUuid)
        }
        verifyAll()
    }
}