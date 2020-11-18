package com.vntana.core.rest.facade.client.impl

import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel
import com.vntana.core.rest.facade.client.AbstractClientOrganizationServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:54 PM
 */
class ClientOrganizationCheckSlugAvailabilityServiceFacadeUnitTest : AbstractClientOrganizationServiceFacadeUnitTest() {

    @Test
    fun `test checkSlugAvailability when slug not valid`() {
        // test data
        resetAll()
        val request = restHelper.buildCheckAvailableClientOrganizationSlugRequest()
        // expectations
        expect(slugValidationComponent.validate(request.slug)).andReturn(false)
        replayAll()
        // test scenario
        val resultResponse = clientOrganizationServiceFacade.checkSlugAvailability(request)
        restHelper.assertBasicErrorResultResponse(resultResponse, ClientOrganizationErrorResponseModel.SLUG_NOT_VALID)
        verifyAll()
    }

    @Test
    fun `test checkSlugAvailability when is available at first iteration`() {
        // test data
        resetAll()
        val request = restHelper.buildCheckAvailableClientOrganizationSlugRequest()
        // expectations
        expect(slugValidationComponent.validate(request.slug)).andReturn(true)
        expect(organizationClientService.findBySlugAndOrganization(request.slug, request.organizationUuid)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        val resultResponse = clientOrganizationServiceFacade.checkSlugAvailability(request)
        restHelper.assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().isAvailable).isTrue()
        assertThat(resultResponse.response().suggested).isEqualTo(request.slug)
        verifyAll()
    }

    @Test
    fun `test checkSlugAvailability when is not available - aka not from first iteration`() {
        // test data
        resetAll()
        val slug = uuid()
        val clientOrganization = commonTestHelper.buildClientOrganization()
        val request = restHelper.buildCheckAvailableClientOrganizationSlugRequest(slug = slug)
        // expectations
        expect(slugValidationComponent.validate(slug)).andReturn(true)
        expect(organizationClientService.findBySlugAndOrganization(slug, request.organizationUuid)).andReturn(Optional.of(clientOrganization))
        expect(organizationClientService.findBySlugAndOrganization("${slug}1", request.organizationUuid)).andReturn(Optional.of(clientOrganization))
        expect(organizationClientService.findBySlugAndOrganization("${slug}2", request.organizationUuid)).andReturn(Optional.of(clientOrganization))
        expect(organizationClientService.findBySlugAndOrganization("${slug}3", request.organizationUuid)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        val resultResponse = clientOrganizationServiceFacade.checkSlugAvailability(request)
        restHelper.assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().isAvailable).isFalse()
        assertThat(resultResponse.response().suggested).isEqualTo("${slug}3")
        verifyAll()
    }
}