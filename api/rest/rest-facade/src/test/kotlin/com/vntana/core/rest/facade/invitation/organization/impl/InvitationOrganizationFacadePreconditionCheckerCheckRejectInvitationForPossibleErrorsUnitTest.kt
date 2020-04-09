package com.vntana.core.rest.facade.invitation.organization.impl

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.rest.facade.invitation.organization.AbstractInvitationOrganizationFacadePreconditionCheckerFacadeUnitTest
import org.apache.http.HttpStatus
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

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
        expect(tokenService.findByToken(token)).andReturn(Optional.empty())
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
        expect(tokenService.findByToken(request.token)).andReturn(Optional.of(tokenCommonHelper.buildTokenInvitationOrganization(token = request.token)))
        expect(invitationOrganizationService.existsByUuid(request.uuid)).andReturn(false)
        replayAll()
        preconditionChecker.checkRejectInvitationForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test check when invitation already rejected`() {
        val request = restTestHelper.buildRejectInvitationOrganizationRequest()
        val tokenInvitationOrganization = tokenCommonHelper.buildTokenInvitationOrganization(token = request.token)
        tokenInvitationOrganization.expire()
        resetAll()
        expect(tokenService.findByToken(request.token)).andReturn(Optional.of(tokenInvitationOrganization))
        expect(invitationOrganizationService.existsByUuid(request.uuid)).andReturn(true)
        expect(invitationOrganizationService.getByUuid(request.uuid)).andReturn(invitationOrganizationCommonTestHelper.buildInvitationOrganization(status = InvitationStatus.REJECTED))
        replayAll()
        preconditionChecker.checkRejectInvitationForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.ALREADY_REJECTED_INVITATION)
        }
        verifyAll()
    }

    @Test
    fun `test check when token is not expire`() {
        val request = restTestHelper.buildRejectInvitationOrganizationRequest()
        val tokenInvitationOrganization = tokenCommonHelper.buildTokenInvitationOrganization(token = request.token)
        resetAll()
        expect(tokenService.findByToken(request.token)).andReturn(Optional.of(tokenInvitationOrganization))
        expect(invitationOrganizationService.existsByUuid(request.uuid)).andReturn(true)
        replayAll()
        assertThat(preconditionChecker.checkRejectInvitationForPossibleErrors(request).isPresent).isFalse()
        verifyAll()
    }

    @Test
    fun `test check when token already expired but status is not rejected`() {
        val request = restTestHelper.buildRejectInvitationOrganizationRequest()
        val tokenInvitationOrganization = tokenCommonHelper.buildTokenInvitationOrganization(token = request.token)
        tokenInvitationOrganization.expire()
        resetAll()
        expect(tokenService.findByToken(request.token)).andReturn(Optional.of(tokenInvitationOrganization))
        expect(invitationOrganizationService.existsByUuid(request.uuid)).andReturn(true)
        expect(invitationOrganizationService.getByUuid(request.uuid)).andReturn(invitationOrganizationCommonTestHelper.buildInvitationOrganization())
        replayAll()
        assertThat(preconditionChecker.checkRejectInvitationForPossibleErrors(request).isPresent).isFalse()
        verifyAll()
    }
}