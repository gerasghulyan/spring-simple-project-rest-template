package com.vntana.core.rest.facade.organization.impl

import com.vntana.commons.api.utils.SingleErrorWithStatus
import com.vntana.core.model.organization.error.OrganizationErrorResponseModel
import com.vntana.core.rest.facade.organization.AbstractOrganizationServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Geras Ghulyan.
 * Date: 10/9/19
 * Time: 3:54 PM
 */
class OrganizationGetOrganizationInvitationServiceFacadeUnitTest : AbstractOrganizationServiceFacadeUnitTest() {

    @Test
    fun `test getOrganizationInvitation when precondititon has error`() {
        resetAll()
        val organization = commonTestHelper.buildOrganizationWithInvitation()
        val httpStatusCode = randomInt()
        val errorModel = OrganizationErrorResponseModel.MISSING_UUID
        expect(organizationServiceFacadePreconditionCheckerComponent.checkGetOrganizationInvitation(organization.uuid)).andReturn(SingleErrorWithStatus.of(httpStatusCode, errorModel))
        replayAll()
        assertBasicErrorResultResponse(organizationServiceFacade.getOrganizationInvitation(organization.uuid), errorModel)
        verifyAll()
    }

    @Test
    fun `test getOrganizationInvitation`() {
        // test data
        resetAll()
        val organization = commonTestHelper.buildOrganizationWithInvitation()
        // expectations
        expect(organizationServiceFacadePreconditionCheckerComponent.checkGetOrganizationInvitation(organization.uuid)).andReturn(SingleErrorWithStatus.empty())
        expect(organizationService.getByUuid(organization.uuid)).andReturn(organization)
        replayAll()
        // test scenario
        val resultResponse = organizationServiceFacade.getOrganizationInvitation(organization.uuid)
        restHelper.assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().uuid).isEqualTo(organization.uuid)
        assertThat(resultResponse.response().customerSubscriptionDefinitionUuid).isEqualTo(organization.invitation.customerSubscriptionDefinitionUuid)
        verifyAll()
    }
}