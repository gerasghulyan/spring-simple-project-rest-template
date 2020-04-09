package com.vntana.core.rest.facade.invitation.organization.impl

import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.rest.facade.invitation.organization.AbstractInvitationOrganizationFacadePreconditionCheckerFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 4/9/2020
 * Time: 2:52 PM
 */
class InvitationOrganizationFacadePreconditionCheckerCheckRejectInvitationForPossibleErrorsUnitTest : AbstractInvitationOrganizationFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test check when token does not exist`() {
        val token = uuid()
        resetAll()
        expect(tokenService.existsByToken(token)).andReturn(false)
        replayAll()
        preconditionChecker.checkRejectInvitationForPossibleErrors(restTestHelper.buildRejectInvitationOrganizationRequest(token = token)).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.TOKEN_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test check when invitation does not exist`() {
        val request = restTestHelper.buildRejectInvitationOrganizationRequest()
        resetAll()
        expect(tokenService.existsByToken(request.token)).andReturn(true)
        expect(invitationOrganizationService.existsByUuid(request.uuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkRejectInvitationForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test check`() {
        val request = restTestHelper.buildRejectInvitationOrganizationRequest()
        resetAll()
        expect(tokenService.existsByToken(request.token)).andReturn(true)
        expect(invitationOrganizationService.existsByUuid(request.uuid)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkRejectInvitationForPossibleErrors(request).isPresent).isFalse()
        verifyAll()
    }
}