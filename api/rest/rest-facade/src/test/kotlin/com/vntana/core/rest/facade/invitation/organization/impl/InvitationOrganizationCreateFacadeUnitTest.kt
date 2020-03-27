package com.vntana.core.rest.facade.invitation.organization.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.rest.facade.invitation.organization.AbstractInvitationOrganizationFacadeUnitTest
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 3:38 PM
 */
class InvitationOrganizationCreateFacadeUnitTest : AbstractInvitationOrganizationFacadeUnitTest() {

    @Test
    fun `test when precondition check failed`() {
        resetAll()
        val request = restTestHelper.buildCreateInvitationOrganizationRequest()
        expect(preconditionChecker.checkCreateForPossibleErrors(request))
                .andReturn(SingleErrorWithStatus.of(404, InvitationOrganizationErrorResponseModel.NOT_FOUND))
        replayAll()
        assertBasicErrorResultResponse(invitationOrganizationServiceFacade.create(request), InvitationOrganizationErrorResponseModel.NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val request = restTestHelper.buildCreateInvitationOrganizationRequest()
        val dto = commonTestHelper.buildCreateInvitationOrganizationDto(
                ownerFullName = request.ownerFullName,
                email = request.email,
                organizationName = request.organizationName,
                slug = request.slug,
                customerSubscriptionDefinitionUuid = request.customerSubscriptionDefinitionUuid
        )
        val invitationOrganization = commonTestHelper.buildInvitationOrganization()
        expect(preconditionChecker.checkCreateForPossibleErrors(request)).andReturn(SingleErrorWithStatus.empty())
        expect(invitationOrganizationService.create(dto)).andReturn(invitationOrganization)
        expect(invitationOrganizationUuidAwareLifecycleMediator.onCreated(invitationOrganization.uuid))
        replayAll()
        assertBasicSuccessResultResponse(invitationOrganizationServiceFacade.create(request))
        verifyAll()
    }
}