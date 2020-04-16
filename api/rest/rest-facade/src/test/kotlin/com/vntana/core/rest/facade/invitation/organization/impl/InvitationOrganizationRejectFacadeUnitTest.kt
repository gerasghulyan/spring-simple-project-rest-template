package com.vntana.core.rest.facade.invitation.organization.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.rest.facade.invitation.organization.AbstractInvitationOrganizationFacadeUnitTest
import org.easymock.EasyMock.anyObject
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 4/9/2020
 * Time: 11:21 AM
 */
class InvitationOrganizationRejectFacadeUnitTest : AbstractInvitationOrganizationFacadeUnitTest() {

    @Test
    fun `test when precondition check failed`() {
        val request = restTestHelper.buildRejectInvitationOrganizationRequest()
        resetAll()
        expect(preconditionChecker.checkRejectInvitationForPossibleErrors(request))
                .andReturn(SingleErrorWithStatus.of(404, InvitationOrganizationErrorResponseModel.NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(invitationOrganizationServiceFacade.reject(request), InvitationOrganizationErrorResponseModel.NOT_FOUND)
        verifyAll()
    }

    @Test
    fun `test reject`() {
        val request = restTestHelper.buildRejectInvitationOrganizationRequest()
        val tokenInvitationOrganization = tokenInvitationOrganizationCommonTestHelper.buildTokenInvitationOrganization()
        val dto = commonTestHelper.buildUpdateInvitationOrganizationStatusDto(
                status = InvitationStatus.REJECTED
        )
        val rejectedInvitationOrganization = commonTestHelper.buildInvitationOrganization()
        resetAll()
        expect(preconditionChecker.checkRejectInvitationForPossibleErrors(request)).andReturn(SingleErrorWithStatus.empty())
        expect(tokenInvitationOrganizationService.getByToken(request.getToken())).andReturn(tokenInvitationOrganization)
        expect(tokenService.findByTokenAndExpire(request.token)).andReturn(anyObject())
        expect(invitationOrganizationService.updateStatus(dto)).andReturn(rejectedInvitationOrganization)
        expect(invitationOrganizationUuidAwareLifecycleMediator.onUpdated(rejectedInvitationOrganization.uuid)).andVoid()
        replayAll()
        assertBasicSuccessResultResponse(invitationOrganizationServiceFacade.reject(request))
        verifyAll()
    }
}