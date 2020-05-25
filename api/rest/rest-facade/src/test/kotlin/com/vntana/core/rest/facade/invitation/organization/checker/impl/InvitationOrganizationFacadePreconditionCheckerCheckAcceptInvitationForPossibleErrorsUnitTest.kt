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
 * Created by Geras Ghulyan
 * Date: 4/9/2020
 * Time: 2:52 PM
 */
class InvitationOrganizationFacadePreconditionCheckerCheckAcceptInvitationForPossibleErrorsUnitTest : AbstractInvitationOrganizationFacadePreconditionCheckerFacadeUnitTest() {

    @Test
    fun `test check when TOKEN_NOT_FOUND`() {
        val request = restTestHelper.buildAcceptInvitationOrganizationRequest()
        resetAll()
        expect(tokenInvitationOrganizationService.findByToken(request.token)).andReturn(Optional.empty())
        replayAll()
        preconditionChecker.checkAcceptInvitationForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_NOT_FOUND)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.TOKEN_NOT_FOUND)
        }
        verifyAll()
    }

    @Test
    fun `test check when token status is ALREADY_REJECTED_INVITATION`() {
        val request = restTestHelper.buildAcceptInvitationOrganizationRequest()
        val tokenInvitationOrganization = tokenInvitationOrganizationCommonTestHelper.buildTokenInvitationOrganization(
                invitationOrganization = invitationOrganizationCommonTestHelper.buildInvitationOrganization(
                        status = InvitationStatus.REJECTED
                )
        )
        resetAll()
        expect(tokenInvitationOrganizationService.findByToken(request.token)).andReturn(Optional.of(tokenInvitationOrganization))
        replayAll()
        preconditionChecker.checkAcceptInvitationForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.ALREADY_REJECTED_INVITATION)
        }
        verifyAll()
    }

    @Test
    fun `test check when token status is ALREADY_ACCEPTED_INVITATION`() {
        val request = restTestHelper.buildAcceptInvitationOrganizationRequest()
        val tokenInvitationOrganization = tokenInvitationOrganizationCommonTestHelper.buildTokenInvitationOrganization(
                invitationOrganization = invitationOrganizationCommonTestHelper.buildInvitationOrganization(
                        status = InvitationStatus.ACCEPTED
                )
        )
        resetAll()
        expect(tokenInvitationOrganizationService.findByToken(request.token)).andReturn(Optional.of(tokenInvitationOrganization))
        replayAll()
        preconditionChecker.checkAcceptInvitationForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.ALREADY_ACCEPTED_INVITATION)
        }
        verifyAll()
    }

    @Test
    fun `test check when token TOKEN_IS_EXPIRED`() {
        val request = restTestHelper.buildAcceptInvitationOrganizationRequest()
        val tokenInvitationOrganization = tokenInvitationOrganizationCommonTestHelper.buildTokenInvitationOrganization()
        tokenInvitationOrganization.expire()
        resetAll()
        expect(tokenInvitationOrganizationService.findByToken(request.token)).andReturn(Optional.of(tokenInvitationOrganization))
        replayAll()
        preconditionChecker.checkAcceptInvitationForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.TOKEN_IS_EXPIRED)
        }
        verifyAll()
    }

    @Test
    fun `test check when SLUG_IS_NOT_AVAILABLE`() {
        val request = restTestHelper.buildAcceptInvitationOrganizationRequest()
        val tokenInvitationOrganization = tokenInvitationOrganizationCommonTestHelper.buildTokenInvitationOrganization()
        val organization = organizationCommonTestHelper.buildOrganization()
        resetAll()
        expect(tokenInvitationOrganizationService.findByToken(request.token)).andReturn(Optional.of(tokenInvitationOrganization))
        expect(organizationService.findBySlug(request.organizationSlug)).andReturn(Optional.of(organization))
        replayAll()
        preconditionChecker.checkAcceptInvitationForPossibleErrors(request).let {
            assertThat(it.httpStatus).isEqualTo(HttpStatus.SC_CONFLICT)
            assertThat(it.error).isEqualTo(InvitationOrganizationErrorResponseModel.SLUG_IS_NOT_AVAILABLE)
        }
        verifyAll()
    }
}