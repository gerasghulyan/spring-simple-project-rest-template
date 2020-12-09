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
 * Created by Manuk Gharslyan.
 * Date: 5/12/20
 * Time: 4:39 PM
 */
class UserRolePreconditionCheckerCheckGrantClientRoleComponentUnitTest : AbstractUserRoleFacadePreconditionCheckerComponentUnitTest() {

    @Test
    fun `test when organization not found`() {
        resetAll()
        val request = restTestHelper.buildUserRoleGrantClientRequest()
        expect(organizationClientService.existsByUuid(request.clientUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkGrantClientRole(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.CLIENT_ORGANIZATION_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when user not found`() {
        resetAll()
        val request = restTestHelper.buildUserRoleGrantClientRequest()
        expect(organizationClientService.existsByUuid(request.clientUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkGrantClientRole(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.USER_NOT_FOUND)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test when requested client role already exists`() {
        resetAll()
        val request = restTestHelper.buildUserRoleGrantClientRequest()
        expect(organizationClientService.existsByUuid(request.clientUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.findByClientOrganizationAndUser(request.clientUuid, request.userUuid)).andReturn(Optional.of(commonTestHelper.buildUserClientAdminRole()))
        replayAll()
        preconditionChecker.checkGrantClientRole(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.REQUESTED_ROLE_ALREADY_GRANTED)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
        }
        verifyAll()
    }

    @Test
    fun `test when requested role does not exist but organization owner level role already exists`() {
        resetAll()
        val request = restTestHelper.buildUserRoleGrantClientRequest()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        expect(organizationClientService.existsByUuid(request.clientUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.findByClientOrganizationAndUser(request.clientUuid, request.userUuid)).andReturn(Optional.empty())
        expect(organizationClientService.getByUuid(request.clientUuid)).andReturn(clientOrganization)
        expect(userRoleService.findByOrganizationAndUser(clientOrganization.organization.uuid, request.userUuid)).andReturn(Optional.of(commonTestHelper.buildUserOrganizationOwnerRole()))
        replayAll()
        preconditionChecker.checkGrantClientRole(request).let {
            assertThat(it.error).isEqualTo(UserRoleErrorResponseModel.REQUESTED_ROLE_ALREADY_GRANTED)
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
        }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildUserRoleGrantClientRequest()
        val clientOrganization = clientOrganizationCommonTestHelper.buildClientOrganization()
        expect(organizationClientService.existsByUuid(request.clientUuid)).andReturn(true)
        expect(userService.existsByUuid(request.userUuid)).andReturn(true)
        expect(userRoleService.findByClientOrganizationAndUser(request.clientUuid, request.userUuid)).andReturn(Optional.empty())
        expect(organizationClientService.getByUuid(request.clientUuid)).andReturn(clientOrganization)
        expect(userRoleService.findByOrganizationAndUser(clientOrganization.organization.uuid, request.userUuid)).andReturn(Optional.empty())
        replayAll()
        assertThat(preconditionChecker.checkGrantClientRole(request)).isEqualTo(SingleErrorWithStatus.empty<UserRoleErrorResponseModel>())
        verifyAll()
    }
}