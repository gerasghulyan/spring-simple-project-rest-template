package com.vntana.core.rest.facade.user.role.component.impl

import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel
import com.vntana.core.rest.facade.user.role.component.AbstractUserRoleFacadePreconditionCheckerComponentUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 12/9/2020
 * Time: 5:07 PM
 */
class UserRolePreconditionCheckerCheckRevokeOrganizationClientsRolesComponentUnitTest : AbstractUserRoleFacadePreconditionCheckerComponentUnitTest() {

    @Test
    fun `test when organization not found`() {
        resetAll()
        val request = restTestHelper.buildUserRoleRevokeOrganizationClientsRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkRevokeOrganizationClientsRoles(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.ORGANIZATION_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when user not found`() {
        resetAll()
        val request = restTestHelper.buildUserRoleRevokeOrganizationClientsRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkRevokeOrganizationClientsRoles(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.USER_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when user has organization related role`() {
        resetAll()
        val request = restTestHelper.buildUserRoleRevokeOrganizationClientsRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(Optional.of(commonTestHelper.buildUserOrganizationAdminRole()))
        replayAll()
        preconditionChecker.checkRevokeOrganizationClientsRoles(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.REVOCABLE_USER_HAS_ORGANIZATION_ROLE)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
        }
        verifyAll()
    }

    @Test
    fun `test when user does not have a client related role in organization`() {
        resetAll()
        val request = restTestHelper.buildUserRoleRevokeOrganizationClientsRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(Optional.empty())
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(listOf())
        replayAll()
        preconditionChecker.checkRevokeOrganizationClientsRoles(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.REVOCABLE_USER_DOES_NOT_HAVE_CLIENT_ROLE)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildUserRoleRevokeOrganizationClientsRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(Optional.empty())
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(listOf(commonTestHelper.buildUserClientContentManagerRole()))
        replayAll()
        assertThat(preconditionChecker.checkRevokeOrganizationClientsRoles(request).isPresent).isEqualTo(false)
        verifyAll()
    }
}