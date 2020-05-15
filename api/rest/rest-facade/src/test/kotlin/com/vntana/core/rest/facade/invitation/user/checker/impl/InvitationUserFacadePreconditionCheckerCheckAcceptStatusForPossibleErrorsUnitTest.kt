package com.vntana.core.rest.facade.invitation.user.checker.impl

import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.facade.invitation.user.checker.AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 6:02 PM
 */
class InvitationUserFacadePreconditionCheckerCheckAcceptStatusForPossibleErrorsUnitTest : AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test when invitation not found for token`() {
        resetAll()
        val request = invitationUserRestTestHelper.buildAcceptInvitationUserRequest()
        expect(tokenInvitationUserService.findByToken(request.token)).andReturn(Optional.empty())
        replayAll()
        preconditionChecker.checkAcceptForPossibleErrors(request).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN)
        }
        verifyAll()
    }

    @Test
    fun `test when role already granted`() {
        resetAll()
        val request = invitationUserRestTestHelper.buildAcceptInvitationUserRequest()
        val adminRole = userRoleCommonTestHelper.buildUserOrganizationAdminRole()
        val tokenInvitationUser = tokenCommonTestHelper.buildTokenInvitationUser()
        val invitationUser = tokenInvitationUser.invitationUser
        val organization = invitationUser.organization
        val user = userCommonTestHelper.buildUser()
        expect(tokenInvitationUserService.findByToken(request.token)).andReturn(Optional.of(tokenInvitationUser))
        expect(userService.getByEmail(invitationUser.email)).andReturn(user)
        expect(userRoleService.findByOrganizationAndUser(organization.uuid, user.uuid)).andReturn(Optional.of(adminRole))
        replayAll()
        preconditionChecker.checkAcceptForPossibleErrors(request).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.USER_ALREADY_HAS_ROLE_IN_ORGANIZATION)
        }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = invitationUserRestTestHelper.buildAcceptInvitationUserRequest()
        val tokenInvitationUser = tokenCommonTestHelper.buildTokenInvitationUser()
        val invitationUser = tokenInvitationUser.invitationUser
        val organization = invitationUser.organization
        val user = userCommonTestHelper.buildUser()
        expect(tokenInvitationUserService.findByToken(request.token)).andReturn(Optional.of(tokenInvitationUser))
        expect(userService.getByEmail(invitationUser.email)).andReturn(user)
        expect(userRoleService.findByOrganizationAndUser(organization.uuid, user.uuid)).andReturn(Optional.empty())
        replayAll()
        assertThat(preconditionChecker.checkAcceptForPossibleErrors(request).isPresent).isFalse()
        verifyAll()
    }
}