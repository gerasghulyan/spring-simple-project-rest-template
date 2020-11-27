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
class InvitationUserFacadePreconditionCheckerCheckGetInvitationToOrganizationByTokenForPossibleErrorsUnitTest : AbstractInvitationUserFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test with missing invitation token`() {
        resetAll()
        replayAll()
        preconditionChecker.checkGetByTokenInvitationToOrganizationForPossibleErrors(null).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.MISSING_INVITATION_TOKEN)
        }
        preconditionChecker.checkGetByTokenInvitationToOrganizationForPossibleErrors(emptyString()).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_UNPROCESSABLE_ENTITY)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.MISSING_INVITATION_TOKEN)
        }
        verifyAll()
    }


    @Test
    fun `test when invitation token does not exist`() {
        val token = uuid()
        resetAll()
        expect(tokenInvitationUserService.existsInvitationToOrganizationByToken(token)).andReturn(false)
        replayAll()
        preconditionChecker.checkGetByTokenInvitationToOrganizationForPossibleErrors(token).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN)
        }
        verifyAll()
    }

    @Test
    fun `test when invitation token has been expired`() {
        val token = uuid()
        resetAll()
        expect(tokenInvitationUserService.existsInvitationToOrganizationByToken(token)).andReturn(true)
        expect(tokenInvitationUserService.isInvitationToOrganizationExpired(token)).andReturn(true)
        replayAll()
        preconditionChecker.checkGetByTokenInvitationToOrganizationForPossibleErrors(token).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_BAD_REQUEST)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVALID_INVITATION_TOKEN)
        }
        verifyAll()
    }

    @Test
    fun `test when invitation user does not exist`() {
        val token = uuid()
        resetAll()
        expect(tokenInvitationUserService.existsInvitationToOrganizationByToken(token)).andReturn(true)
        expect(tokenInvitationUserService.isInvitationToOrganizationExpired(token)).andReturn(false)
        expect(invitationUserToOrganizationService.existsByToken(token)).andReturn(false)
        replayAll()
        preconditionChecker.checkGetByTokenInvitationToOrganizationForPossibleErrors(token).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationUserErrorResponseModel.INVITATION_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun test() {
        val token = uuid()
        resetAll()
        expect(tokenInvitationUserService.existsInvitationToOrganizationByToken(token)).andReturn(true)
        expect(tokenInvitationUserService.isInvitationToOrganizationExpired(token)).andReturn(false)
        expect(invitationUserToOrganizationService.existsByToken(token)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkGetByTokenInvitationToOrganizationForPossibleErrors(token).isPresent).isFalse()
        verifyAll()
    }
}