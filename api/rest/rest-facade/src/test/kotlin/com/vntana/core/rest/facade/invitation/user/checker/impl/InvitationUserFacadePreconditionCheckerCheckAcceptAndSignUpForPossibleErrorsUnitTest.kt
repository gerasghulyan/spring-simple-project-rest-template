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
 * Date: 5/15/20
 * Time: 5:17 PM
 */
class InvitationUserFacadePreconditionCheckerCheckAcceptAndSignUpForPossibleErrorsUnitTest : AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test when invitation not found for token`() {
        resetAll()
        val request = invitationUserRestTestHelper.buildAcceptInvitationUserAndSignUpRequest()
        expect(tokenInvitationUserService.findByToken(request.token)).andReturn(Optional.empty())
        replayAll()
        preconditionChecker.checkAcceptAndSignUpForPossibleErrors(request).let {
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
        val tokenInvitationUser = tokenCommonTestHelper.buildTokenInvitationUser()
        tokenInvitationUser.expire()
        val invitationUser = tokenInvitationUser.invitationUser
        val organization = invitationUser.organization
        val user = userCommonTestHelper.buildUser()
        expect(tokenInvitationUserService.findByToken(request.token)).andReturn(Optional.of(tokenInvitationUser))
        replayAll()
        preconditionChecker.checkAcceptAndSignUpForPossibleErrors(request).let {
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
        val tokenInvitationUser = tokenCommonTestHelper.buildTokenInvitationUser()
        val invitationUser = tokenInvitationUser.invitationUser
        expect(tokenInvitationUserService.findByToken(request.token)).andReturn(Optional.of(tokenInvitationUser))
        expect(userService.existsByEmail(invitationUser.email)).andReturn(true)
        replayAll()
        preconditionChecker.checkAcceptAndSignUpForPossibleErrors(request).let {
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
        val tokenInvitationUser = tokenCommonTestHelper.buildTokenInvitationUser()
        val invitationUser = tokenInvitationUser.invitationUser
        val organization = invitationUser.organization
        val user = userCommonTestHelper.buildUser()
        expect(tokenInvitationUserService.findByToken(request.token)).andReturn(Optional.of(tokenInvitationUser))
        expect(userService.existsByEmail(invitationUser.email)).andReturn(false)

        replayAll()
        assertThat(preconditionChecker.checkAcceptAndSignUpForPossibleErrors(request).isPresent).isFalse()
        verifyAll()
    }
}