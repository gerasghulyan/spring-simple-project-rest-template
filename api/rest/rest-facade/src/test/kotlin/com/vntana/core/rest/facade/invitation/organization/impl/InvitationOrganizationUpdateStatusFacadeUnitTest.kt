package com.vntana.core.rest.facade.invitation.organization.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.rest.facade.invitation.organization.AbstractInvitationOrganizationFacadeUnitTest
import com.vntana.core.service.invitation.organization.dto.UpdateInvitationOrganizationStatusDto
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 4/9/2020
 * Time: 11:21 AM
 */
class InvitationOrganizationUpdateStatusFacadeUnitTest : AbstractInvitationOrganizationFacadeUnitTest() {

    @Test
    fun `test when precondition check failed`() {
        val request = restTestHelper.buildUpdateInvitationOrganizationStatusRequest()
        resetAll()
        expect(preconditionChecker.checkGetByUuidForPossibleErrors(request.uuid))
                .andReturn(SingleErrorWithStatus.of(404, InvitationOrganizationErrorResponseModel.NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(invitationOrganizationServiceFacade.updateStatus(request), InvitationOrganizationErrorResponseModel.NOT_FOUND)
        verifyAll()
    }

    @Test
    fun `test update status`() {
        val request = restTestHelper.buildUpdateInvitationOrganizationStatusRequest()
        val dto = commonTestHelper.buildUpdateInvitationOrganizationStatusDto()
        resetAll()
        expect(preconditionChecker.checkGetByUuidForPossibleErrors(request.uuid))
                .andReturn(SingleErrorWithStatus.empty())
        expect(mapperFacade.map(request, UpdateInvitationOrganizationStatusDto::class.java)).andReturn(dto)
        expect(invitationOrganizationService.updateStatus(dto)).andReturn(commonTestHelper.buildInvitationOrganization())
        replayAll()
        assertBasicSuccessResultResponse(invitationOrganizationServiceFacade.updateStatus(request))
        verifyAll()
    }
}