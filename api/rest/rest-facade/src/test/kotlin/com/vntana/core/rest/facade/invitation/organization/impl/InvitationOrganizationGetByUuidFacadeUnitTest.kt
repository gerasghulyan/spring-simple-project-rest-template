package com.vntana.core.rest.facade.invitation.organization.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.model.invitation.organization.response.model.GetInvitationOrganizationResponseModel
import com.vntana.core.rest.facade.invitation.organization.AbstractInvitationOrganizationFacadeUnitTest
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 3:52 PM
 */
class InvitationOrganizationGetByUuidFacadeUnitTest : AbstractInvitationOrganizationFacadeUnitTest() {

    @Test
    fun `test when precondition check failed`() {
        resetAll()
        val uuid = uuid()
        expect(preconditionChecker.checkGetByUuidForPossibleErrors(uuid))
                .andReturn(SingleErrorWithStatus.of(404, InvitationOrganizationErrorResponseModel.NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(invitationOrganizationServiceFacade.getByUuid(uuid), InvitationOrganizationErrorResponseModel.NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val uuid = uuid()
        val invitationOrganization = commonTestHelper.buildInvitationOrganization()
        invitationOrganization.uuid = uuid
        expect(preconditionChecker.checkGetByUuidForPossibleErrors(uuid)).andReturn(SingleErrorWithStatus.empty())
        expect(invitationOrganizationService.getByUuid(uuid)).andReturn(invitationOrganization)
        expect(mapperFacade.map(invitationOrganization, GetInvitationOrganizationResponseModel::class.java)).andReturn(GetInvitationOrganizationResponseModel())
        replayAll()
        assertBasicSuccessResultResponse(invitationOrganizationServiceFacade.getByUuid(uuid))
        verifyAll()
    }
}