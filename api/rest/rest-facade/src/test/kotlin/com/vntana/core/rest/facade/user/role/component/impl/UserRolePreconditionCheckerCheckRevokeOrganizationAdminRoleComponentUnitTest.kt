package com.vntana.core.rest.facade.user.role.component.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.domain.user.UserRole
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel
import com.vntana.core.rest.facade.user.role.component.AbstractUserRoleFacadePreconditionCheckerComponentUnitTest
import org.apache.http.HttpStatus.SC_CONFLICT
import org.apache.http.HttpStatus.SC_NOT_FOUND
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 11:53 AM
 */
class UserRolePreconditionCheckerCheckRevokeOrganizationAdminRoleComponentUnitTest : AbstractUserRoleFacadePreconditionCheckerComponentUnitTest() {

    @Test
    fun `test when organization not found`() {
        resetAll()
        val request = restTestHelper.buildUserRoleRevokeOrganizationAdminRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkRevokeOrganizationAdminRole(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.ORGANIZATION_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when user not found`() {
        resetAll()
        val request = restTestHelper.buildUserRoleRevokeOrganizationAdminRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkRevokeOrganizationAdminRole(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.USER_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when requested role not found`() {
        resetAll()
        val request = restTestHelper.buildUserRoleRevokeOrganizationAdminRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.existsByOrganizationAndUserAndRole(request.organizationUuid, request.userUuid, UserRole.ORGANIZATION_ADMIN)).andReturn(false)
        replayAll()
        preconditionChecker.checkRevokeOrganizationAdminRole(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.REQUESTED_ROLE_IS_ABSENT)
            assertThat(it.httpStatus).isEqualTo(SC_CONFLICT)
        }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildUserRoleRevokeOrganizationAdminRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.existsByOrganizationAndUserAndRole(request.organizationUuid, request.userUuid, UserRole.ORGANIZATION_ADMIN)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkRevokeOrganizationAdminRole(request)).isEqualTo(SingleErrorWithStatus.empty<UserRoleErrorResponseModel>())
        verifyAll()
    }
}