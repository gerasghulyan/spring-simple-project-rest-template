package com.vntana.core.rest.facade.invitation.organization.checker.impl

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.rest.facade.invitation.organization.checker.AbstractInvitationOrganizationFacadePreconditionCheckerFacadeUnitTest
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
    fun `test check when TOKEN_NOT_FOUND`() {
        val request = restTestHelper.buildRejectInvitationOrganizationRequest()
        resetAll()
        expect(tokenInvitationOrganizationService.findByToken(request.token)).andReturn(Optional.empty())
        replayAll()
        preconditionChecker.checkRejectInvitationForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.TOKEN_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test check when ALREADY_REJECTED_INVITATION`() {
        val request = restTestHelper.buildRejectInvitationOrganizationRequest()
        val tokenInvitationOrganization = tokenInvitationOrganizationCommonTestHelper.buildTokenInvitationOrganization(
                invitationOrganization = invitationOrganizationCommonTestHelper.buildInvitationOrganization(
                        status = InvitationStatus.REJECTED
                )
        )
        resetAll()
        expect(tokenInvitationOrganizationService.findByToken(request.token)).andReturn(Optional.of(tokenInvitationOrganization))
        replayAll()
        preconditionChecker.checkRejectInvitationForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.ALREADY_REJECTED_INVITATION)
        }
        verifyAll()
    }

    @Test
    fun `test check when ALREADY_ACCEPTED_INVITATION`() {
        val request = restTestHelper.buildRejectInvitationOrganizationRequest()
        val tokenInvitationOrganization = tokenInvitationOrganizationCommonTestHelper.buildTokenInvitationOrganization(
                invitationOrganization = invitationOrganizationCommonTestHelper.buildInvitationOrganization(
                        status = InvitationStatus.ACCEPTED
                )
        )
        resetAll()
        expect(tokenInvitationOrganizationService.findByToken(request.token)).andReturn(Optional.of(tokenInvitationOrganization))
        replayAll()
        preconditionChecker.checkRejectInvitationForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.ALREADY_ACCEPTED_INVITATION)
        }
        verifyAll()
    }

    @Test
    fun `test check when TOKEN_IS_EXPIRED`() {
        val request = restTestHelper.buildRejectInvitationOrganizationRequest()
        val tokenInvitationOrganization = tokenCommonHelper.buildTokenInvitationOrganization(token = request.token)
        tokenInvitationOrganization.expire()
        resetAll()
        expect(tokenInvitationOrganizationService.findByToken(request.token)).andReturn(Optional.of(tokenInvitationOrganization))
        replayAll()
        preconditionChecker.checkRejectInvitationForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.TOKEN_IS_EXPIRED)
        }
        verifyAll()
    }
}