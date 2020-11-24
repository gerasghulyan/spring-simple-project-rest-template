package com.vntana.core.rest.facade.user.role.component.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.user.role.error.UserRoleErrorResponseModel
import com.vntana.core.rest.facade.user.role.component.AbstractUserRoleFacadePreconditionCheckerComponentUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 4:39 PM
 */
class UserRolePreconditionCheckerCheckGrantOrganizationAdminRoleComponentUnitTest : AbstractUserRoleFacadePreconditionCheckerComponentUnitTest() {

    @Test
    fun `test when organization not found`() {
        resetAll()
        val request = restTestHelper.buildUserRoleGrantOrganizationAdminRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkGrantOrganizationAdminRole(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.ORGANIZATION_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when user not found`() {
        resetAll()
        val request = restTestHelper.buildUserRoleGrantOrganizationAdminRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkGrantOrganizationAdminRole(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.USER_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when requested role already exists`() {
        resetAll()
        val request = restTestHelper.buildUserRoleGrantOrganizationAdminRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(Optional.of(commonTestHelper.buildUserOrganizationAdminRole()))
        replayAll()
        preconditionChecker.checkGrantOrganizationAdminRole(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.REQUESTED_ROLE_ALREADY_GRANTED)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
        }
        verifyAll()
    }

    @Test
    fun `test when user already has member role in organization`() {
        resetAll()
        val request = restTestHelper.buildUserRoleGrantOrganizationAdminRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(Optional.empty())
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(listOf(commonTestHelper.buildUserClientViewerRole()))
        replayAll()
        preconditionChecker.checkGrantOrganizationAdminRole(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.REQUESTED_ROLE_ALREADY_GRANTED)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
        }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildUserRoleGrantOrganizationAdminRequest()
        expect(organizationService.existsByUuid(request.organizationUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.findByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(Optional.empty())
        expect(userRoleService.findAllClientOrganizationRoleByOrganizationAndUser(request.organizationUuid, request.userUuid)).andReturn(listOf())
        replayAll()
        assertThat(preconditionChecker.checkGrantOrganizationAdminRole(request)).isEqualTo(SingleErrorWithStatus.empty<UserRoleErrorResponseModel>())
        verifyAll()
    }
}