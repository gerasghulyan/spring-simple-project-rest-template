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
 * Time: 5:17 PM
 */
class InvitationUserToClientFacadePreconditionCheckerCheckAcceptAndSignUpForPossibleErrorsUnitTest : AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test when invitation not found for token`() {
        resetAll()
        val request = invitationUserRestTestHelper.buildAcceptInvitationUserAndSignUpRequest()
        expect(tokenInvitationUserService.findByClientInvitationToken(request.token)).andReturn(Optional.empty())
        replayAll()
        preconditionChecker.checkAcceptAndSignInvitationToClientUpForPossibleErrors(request).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN)
        }
        verifyAll()
    }

    @Test
    fun `test when expired`() {
        resetAll()
        val request = invitationUserRestTestHelper.buildAcceptInvitationUserAndSignUpRequest()
        val userInvitationToken = tokenCommonTestHelper.buildTokenInvitationUserToClient()
        userInvitationToken.expire()
        expect(tokenInvitationUserService.findByClientInvitationToken(request.token)).andReturn(Optional.of(userInvitationToken))
        replayAll()
        preconditionChecker.checkAcceptAndSignInvitationToClientUpForPossibleErrors(request).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_ACCEPTABLE)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.TOKEN_IS_EXPIRED)
        }
        verifyAll()
    }

    @Test
    fun `test when role already granted`() {
        resetAll()
        val request = invitationUserRestTestHelper.buildAcceptInvitationUserAndSignUpRequest()
        val userInvitationToken = tokenCommonTestHelper.buildTokenInvitationUserToClient()
        val userInvitation = userInvitationToken.userInvitation
        expect(tokenInvitationUserService.findByClientInvitationToken(request.token)).andReturn(Optional.of(userInvitationToken))
        expect(userService.existsByEmail(userInvitation.email)).andReturn(true)
        replayAll()
        preconditionChecker.checkAcceptAndSignInvitationToClientUpForPossibleErrors(request).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.USER_ALREADY_EXISTS)
        }
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = invitationUserRestTestHelper.buildAcceptInvitationUserAndSignUpRequest()
        val userInvitationToken = tokenCommonTestHelper.buildTokenInvitationUserToClient()
        val userInvitation = userInvitationToken.userInvitation
        expect(tokenInvitationUserService.findByClientInvitationToken(request.token)).andReturn(Optional.of(userInvitationToken))
        expect(userService.existsByEmail(userInvitation.email)).andReturn(false)
        replayAll()
        assertThat(preconditionChecker.checkAcceptAndSignInvitationToClientUpForPossibleErrors(request).isPresent).isFalse()
        verifyAll()
    }
}