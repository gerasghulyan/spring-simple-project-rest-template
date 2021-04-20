package com.vntana.core.rest.facade.invitation.user.checker.impl

import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.facade.invitation.user.checker.AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Diana Gevorgyan
 * Date: 11/25/20
 * Time: 6:02 PM
 */
class InvitationUserToClientFacadePreconditionCheckerCheckAcceptForPossibleErrorsUnitTest : AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test when invitation not found for token`() {
        resetAll()
        val request = invitationUserRestTestHelper.buildAcceptInvitationUserRequest()
        expect(tokenInvitationUserService.findByClientInvitationToken(request.token)).andReturn(Optional.empty())
        replayAll()
        preconditionChecker.checkAcceptForClientForPossibleErrors(request).let {
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
        val clientAdminRole = userRoleCommonTestHelper.buildUserClientAdminRole()
        val tokenInvitationUser = tokenCommonTestHelper.buildTokenInvitationUserToClient()
        val userInvitation = tokenInvitationUser.userInvitation
        val client = userInvitation.clientOrganization
        val user = userCommonTestHelper.buildUser()
        expect(tokenInvitationUserService.findByClientInvitationToken(request.token)).andReturn(Optional.of(tokenInvitationUser))
        expect(userService.getByEmail(userInvitation.email)).andReturn(user)
        expect(userRoleService.findByOrganizationAndUser(client.organization.uuid, user.uuid)).andReturn(Optional.empty())
        expect(userRoleService.findByClientOrganizationAndUser(client.uuid, user.uuid)).andReturn(Optional.of(clientAdminRole))
        replayAll()
        preconditionChecker.checkAcceptForClientForPossibleErrors(request).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.USER_ALREADY_HAS_ROLE_IN_CLIENT)
        }
        verifyAll()
    }

    @Test
    fun `test when expired`() {
        resetAll()
        val request = invitationUserRestTestHelper.buildAcceptInvitationUserRequest()
        val userInvitationToken = tokenCommonTestHelper.buildTokenInvitationUserToClient()
        userInvitationToken.expire()
        val userInvitation = userInvitationToken.userInvitation
        val client = userInvitation.clientOrganization
        val user = userCommonTestHelper.buildUser()
        expect(tokenInvitationUserService.findByClientInvitationToken(request.token)).andReturn(Optional.of(userInvitationToken))
        expect(userService.getByEmail(userInvitation.email)).andReturn(user)
        expect(userRoleService.findByOrganizationAndUser(client.organization.uuid, user.uuid)).andReturn(Optional.empty())
        expect(userRoleService.findByClientOrganizationAndUser(client.uuid, user.uuid)).andReturn(Optional.empty())
        replayAll()
        preconditionChecker.checkAcceptForClientForPossibleErrors(request).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_ACCEPTABLE)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.TOKEN_IS_EXPIRED)
        }
        verifyAll()
    }

    @Test
    fun `test when USER_ALREADY_HAS_ROLE_IN_ORGANIZATION`() {
        resetAll()
        val request = invitationUserRestTestHelper.buildAcceptInvitationUserRequest()
        val userInvitationToken = tokenCommonTestHelper.buildTokenInvitationUserToClient()
        val userInvitation = userInvitationToken.userInvitation
        val client = userInvitation.clientOrganization
        val user = userCommonTestHelper.buildUser()
        expect(tokenInvitationUserService.findByClientInvitationToken(request.token)).andReturn(Optional.of(userInvitationToken))
        expect(userService.getByEmail(userInvitation.email)).andReturn(user)
        expect(userRoleService.findByOrganizationAndUser(client.organization.uuid, user.uuid)).andReturn(Optional.of(userRoleCommonTestHelper.buildUserOrganizationAdminRole()))
        replayAll()
        preconditionChecker.checkAcceptForClientForPossibleErrors(request).let {
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
        val userInvitationToken = tokenCommonTestHelper.buildTokenInvitationUserToClient()
        val userInvitation = userInvitationToken.userInvitation
        val client = userInvitation.clientOrganization
        val user = userCommonTestHelper.buildUser()
        expect(tokenInvitationUserService.findByClientInvitationToken(request.token)).andReturn(Optional.of(userInvitationToken))
        expect(userService.getByEmail(userInvitation.email)).andReturn(user)
        expect(userRoleService.findByOrganizationAndUser(client.organization.uuid, user.uuid)).andReturn(Optional.empty())
        expect(userRoleService.findByClientOrganizationAndUser(client.uuid, user.uuid)).andReturn(Optional.empty())
        replayAll()
        assertThat(preconditionChecker.checkAcceptForClientForPossibleErrors(request).isPresent).isFalse()
        verifyAll()
    }
}