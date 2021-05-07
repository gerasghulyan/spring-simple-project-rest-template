package com.vntana.core.rest.facade.user.role.component.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel
import com.vntana.core.rest.facade.user.role.component.AbstractUserRoleFacadePreconditionCheckerComponentUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 09.12.2020
 * Time: 17:10
 */
class UserRolePreconditionCheckerCheckRevokeClientRoleComponentUnitTest : AbstractUserRoleFacadePreconditionCheckerComponentUnitTest() {

    @Test
    fun `test when organization not found`() {
        resetAll()
        val request = restTestHelper.buildUserRoleRevokeClientRequest()
        expect(clientOrganizationService.existsByUuid(request.clientUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkRevokeClientRole(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.CLIENT_ORGANIZATION_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when user not found`() {
        resetAll()
        val request = restTestHelper.buildUserRoleRevokeClientRequest()
        expect(clientOrganizationService.existsByUuid(request.clientUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkRevokeClientRole(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.USER_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildUserRoleRevokeClientRequest()
        expect(clientOrganizationService.existsByUuid(request.clientUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkRevokeClientRole(request)).isEqualTo(SingleErrorWithStatus.empty<UserRoleErrorResponseModel>())
        verifyAll()
    }
}