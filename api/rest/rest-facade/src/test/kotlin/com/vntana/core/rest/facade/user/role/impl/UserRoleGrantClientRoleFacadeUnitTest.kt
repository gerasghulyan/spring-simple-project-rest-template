package com.vntana.core.rest.facade.user.role.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.domain.user.UserRole
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel
import com.vntana.core.rest.facade.user.role.AbstractUserRoleServiceFacadeUnitTest
import org.assertj.core.api.Assertions
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 11/12/2020
 * Time: 3:45 PM
 */
class UserRoleGrantClientRoleFacadeUnitTest : AbstractUserRoleServiceFacadeUnitTest() {

    @Test
    fun `test when precondition failed`() {
        resetAll()
        val request = restTestHelper.buildUserRoleGrantClientRequest()
        val error = SingleErrorWithStatus.of(404, UserRoleErrorResponseModel.CLIENT_ORGANIZATION_NOT_FOUND)
        expect(preconditionChecker.checkGrantClientRole(request)).andReturn(error)
        replayAll()
        assertBasicErrorResultResponse(userRoleServiceFacade.grantClientRole(request), error.error)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildUserRoleGrantClientRequest()
        val dto = commonTestHelper.buildUserGrantClientRoleDto(
                userUuid = request.userUuid,
                clientOrganizationUuid = request.clientUuid,
                clientRole = UserRole.valueOf(request.userRole.name)
        )
        val adminRole = commonTestHelper.buildUserClientAdminRole()
        expect(preconditionChecker.checkGrantClientRole(request)).andReturn(SingleErrorWithStatus.empty())
        expect(userRoleService.grantClientRole(dto)).andReturn(adminRole)
        replayAll()
        userRoleServiceFacade.grantClientRole(request).let {
            assertBasicSuccessResultResponse(it)
            Assertions.assertThat(it.response().userUuid).isEqualTo(adminRole.user.uuid)
        }
        verifyAll()
    }
}