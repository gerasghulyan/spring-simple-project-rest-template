package com.vntana.core.rest.facade.client.impl

import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel
import com.vntana.core.rest.facade.client.AbstractClientOrganizationServiceFacadeUnitTest
import com.vntana.core.service.client.dto.UpdateClientOrganizationDto
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan.
 * Date: 10/10/19
 * Time: 11:35 AM
 */
class ClientOrganizationUpdateServiceFacadeUnitTest : AbstractClientOrganizationServiceFacadeUnitTest() {

    @Test
    fun `test update when slug not valid`() {
        // test data
        resetAll()
        val slug = uuid()
        val request = restHelper.buildUpdateClientOrganizationRequest(slug = slug)
        commonTestHelper.buildClientOrganization()
        // expectations
        expect(slugValidationComponent.validate(slug)).andReturn(false)
        replayAll()
        // test scenario
        val resultResponse = clientOrganizationServiceFacade.update(request)
        restHelper.assertBasicErrorResultResponse(resultResponse, ClientOrganizationErrorResponseModel.SLUG_NOT_VALID)
        verifyAll()
    }

    @Test
    fun `test update when client organization already exists for slug`() {
        // test data
        resetAll()
        val slug = uuid()
        val request = restHelper.buildUpdateClientOrganizationRequest(slug = slug)
        val clientOrganization = commonTestHelper.buildClientOrganization()
        val clientOrganization2 = commonTestHelper.buildClientOrganization()
        // expectations
        expect(slugValidationComponent.validate(slug)).andReturn(true)
        expect(clientOrganizationService.getByUuid(request.uuid)).andReturn(clientOrganization)
        expect(clientOrganizationService.findBySlugAndOrganization(
                request.slug,
                clientOrganization.organization.uuid
        )).andReturn(Optional.of(clientOrganization2))
        replayAll()
        // test scenario
        val resultResponse = clientOrganizationServiceFacade.update(request)
        restHelper.assertBasicErrorResultResponse(resultResponse, ClientOrganizationErrorResponseModel.SLUG_ALREADY_EXISTS)
        verifyAll()
    }

    @Test
    fun `test update`() {
        // test data
        resetAll()
        val slug = uuid()
        val request = restHelper.buildUpdateClientOrganizationRequest(slug = slug)
        val dto = commonTestHelper.buildUpdateClientOrganizationDto()
        val clientOrganization = commonTestHelper.buildClientOrganization()
        // expectations
        expect(slugValidationComponent.validate(slug)).andReturn(true)
        expect(clientOrganizationService.getByUuid(request.uuid)).andReturn(clientOrganization)
        expect(clientOrganizationService.findBySlugAndOrganization(
                request.slug,
                clientOrganization.organization.uuid
        )).andReturn(Optional.of(clientOrganization))
        expect(mapperFacade.map(request, UpdateClientOrganizationDto::class.java)).andReturn(dto)
        expect(clientOrganizationService.update(dto)).andReturn(clientOrganization)
        replayAll()
        // test scenario
        val resultResponse = clientOrganizationServiceFacade.update(request)
        restHelper.assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().uuid).isEqualTo(clientOrganization.uuid)
        verifyAll()
    }
}