package com.vntana.core.rest.facade.invitation.user.checker.impl

import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.facade.invitation.user.checker.AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/27/2020
 * Time: 10:12 AM
 */
class InvitationUserFacadePreconditionCheckerCheckGetInvitationToClientByTokenForPossibleErrorsUnitTest : AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test with missing invitation token`() {
        resetAll()
        replayAll()
        preconditionChecker.checkGetByTokenInvitationToClientForPossibleErrors(null).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.MISSING_INVITATION_TOKEN)
        }
        preconditionChecker.checkGetByTokenInvitationToClientForPossibleErrors(emptyString()).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.MISSING_INVITATION_TOKEN)
        }
        verifyAll()
    }


    @Test
    fun `test when invitation token does not exist`() {
        val token = uuid()
        resetAll()
        expect(tokenInvitationUserService.doesInvitationToClientExist(token)).andReturn(false)
        replayAll()
        preconditionChecker.checkGetByTokenInvitationToClientForPossibleErrors(token).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN)
        }
        verifyAll()
    }

    @Test
    fun `test when invitation token has been expired`() {
        val token = uuid()
        resetAll()
        expect(tokenInvitationUserService.doesInvitationToClientExist(token)).andReturn(true)
        expect(tokenInvitationUserService.isInvitationToClientExpired(token)).andReturn(true)
        replayAll()
        preconditionChecker.checkGetByTokenInvitationToClientForPossibleErrors(token).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_BAD_REQUEST)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVALID_INVITATION_TOKEN)
        }
        verifyAll()
    }

    @Test
    fun `test when invitation user does not exist`() {
        val token = uuid()
        resetAll()
        expect(tokenInvitationUserService.doesInvitationToClientExist(token)).andReturn(true)
        expect(tokenInvitationUserService.isInvitationToClientExpired(token)).andReturn(false)
        expect(invitationUserToClientService.existsByToken(token)).andReturn(false)
        replayAll()
        preconditionChecker.checkGetByTokenInvitationToClientForPossibleErrors(token).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVITATION_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun test() {
        val token = uuid()
        resetAll()
        expect(tokenInvitationUserService.doesInvitationToClientExist(token)).andReturn(true)
        expect(tokenInvitationUserService.isInvitationToClientExpired(token)).andReturn(false)
        expect(invitationUserToClientService.existsByToken(token)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkGetByTokenInvitationToClientForPossibleErrors(token).isPresent).isFalse()
        verifyAll()
    }
}