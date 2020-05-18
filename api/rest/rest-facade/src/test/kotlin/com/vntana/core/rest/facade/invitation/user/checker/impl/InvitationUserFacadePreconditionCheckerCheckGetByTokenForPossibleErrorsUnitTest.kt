package com.vntana.core.rest.facade.invitation.user.checker.impl

import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.facade.invitation.user.checker.AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 7:39 PM
 */
class InvitationUserFacadePreconditionCheckerCheckGetByTokenForPossibleErrorsUnitTest : AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test with missing invitation token`() {
        resetAll()
        replayAll()
        preconditionChecker.checkGetByTokenForPossibleErrors(null).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.MISSING_INVITATION_TOKEN)
        }
        preconditionChecker.checkGetByTokenForPossibleErrors(emptyString()).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.MISSING_INVITATION_TOKEN)
        }
        verifyAll()
    }


    @Test
    fun `test when invitation token does not exist`() {
        val token = uuid()
        resetAll()
        expect(tokenInvitationUserService.isExists(token)).andReturn(false)
        replayAll()
        preconditionChecker.checkGetByTokenForPossibleErrors(token).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN)
        }
        verifyAll()
    }

    @Test
    fun `test when invitation token has been expired`() {
        val token = uuid()
        resetAll()
        expect(tokenInvitationUserService.isExists(token)).andReturn(true)
        expect(tokenInvitationUserService.isExpired(token)).andReturn(true)
        replayAll()
        preconditionChecker.checkGetByTokenForPossibleErrors(token).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_BAD_REQUEST)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVALID_INVITATION_TOKEN)
        }
        verifyAll()
    }

    @Test
    fun `test when invitation user does not exist`() {
        val token = uuid()
        resetAll()
        expect(tokenInvitationUserService.isExists(token)).andReturn(true)
        expect(tokenInvitationUserService.isExpired(token)).andReturn(false)
        expect(invitationUserService.existsByToken(token)).andReturn(false)
        replayAll()
        preconditionChecker.checkGetByTokenForPossibleErrors(token).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVITATION_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun test() {
        val token = uuid()
        resetAll()
        expect(tokenInvitationUserService.isExists(token)).andReturn(true)
        expect(tokenInvitationUserService.isExpired(token)).andReturn(false)
        expect(invitationUserService.existsByToken(token)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkGetByTokenForPossibleErrors(token).isPresent).isFalse()
        verifyAll()
    }
}