package com.vntana.core.rest.facade.organization.impl

import com.vntana.core.model.organization.error.OrganizationErrorResponseModel
import com.vntana.core.model.organization.request.OrganizationPayedOutsideStripeRequest
import com.vntana.core.rest.facade.organization.AbstractOrganizationServiceFacadeUnitTest
import com.vntana.core.service.organization.dto.OrganizationPayedOutsideStripeDto
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 1/26/21
 * Time: 12:55 AM
 */
class OrganizationSetPayedOutsideStripeFlagServiceFacadeUnitTet : AbstractOrganizationServiceFacadeUnitTest() {

    @Test
    fun `test setPaymentOutsideStripe when precondition has error`() {
        // test data
        val organization = commonTestHelper.buildOrganizationWithInvitation()
        val isPayedOutsideStripe = true
        val errorModel = OrganizationErrorResponseModel.ORGANIZATION_NOT_FOUND
        resetAll()
        // expectations
        expect(
            organizationService.existsByUuid(
                organization.uuid
            )
        ).andReturn(false)
        replayAll()
        // test scenario
        organizationServiceFacade.setPaymentOutsideStripe(
            OrganizationPayedOutsideStripeRequest(
                organization.uuid,
                isPayedOutsideStripe
            )
        )
            .let {
                assertBasicErrorResultResponse(it, errorModel)
            }
        verifyAll()
    }

    @Test
    fun `test setPaymentOutsideStripe`() {
        // test data
        val organization = commonTestHelper.buildOrganizationWithInvitation()
        val isPayedOutsideStripe = true
        val updatedOrganization = organization
        updatedOrganization.isPayedOutsideStripe = true
        val request = OrganizationPayedOutsideStripeRequest(
            organization.uuid,
            isPayedOutsideStripe
        )
        val dto = OrganizationPayedOutsideStripeDto(
            organization.uuid,
            isPayedOutsideStripe
        )
        resetAll()
        // expectations
        expect(
            organizationService.existsByUuid(
                organization.uuid
            )
        ).andReturn(true)
        expect(mapperFacade.map(request, OrganizationPayedOutsideStripeDto::class.java)).andReturn(dto)
        expect(organizationService.setPaymentOutsideStripe(dto)).andReturn(updatedOrganization)
        replayAll()
        // test scenario
        organizationServiceFacade.setPaymentOutsideStripe(request)
            .let {
                assertBasicSuccessResultResponse(it)
                assertThat(it.response().uuid).isEqualTo(organization.uuid)
                assertThat(it.response().isPayedOutsideStripe).isEqualTo(isPayedOutsideStripe)
            }
        verifyAll()
    }
}