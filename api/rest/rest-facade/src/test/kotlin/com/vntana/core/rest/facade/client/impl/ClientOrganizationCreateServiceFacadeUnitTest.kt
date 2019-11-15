package com.vntana.core.rest.facade.client.impl

import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel
import com.vntana.core.rest.facade.client.AbstractClientOrganizationServiceFacadeUnitTest
import com.vntana.core.service.client.dto.CreateClientOrganizationDto
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/10/19
 * Time: 11:35 AM
 */
class ClientOrganizationCreateServiceFacadeUnitTest : AbstractClientOrganizationServiceFacadeUnitTest() {

    @Test
    fun `test create when client organization already exists for slug`() {
        // test data
        resetAll()
        val slug = uuid()
        val request = restHelper.buildCreateClientOrganizationRequest(slug = slug)
        val clientOrganization = commonTestHelper.buildClientOrganization()
        // expectations
        expect(clientOrganizationService.findBySlugAndOrganization(slug, request.organizationUuid)).andReturn(Optional.of(clientOrganization))
        replayAll()
        // test scenario
        val resultResponse = clientOrganizationServiceFacade.create(request)
        restHelper.assertBasicErrorResultResponse(resultResponse, ClientOrganizationErrorResponseModel.SLUG_ALREADY_EXISTS)
        verifyAll()
    }

    @Test
    fun `test create`() {
        // test data
        resetAll()
        val slug = uuid()
        val request = restHelper.buildCreateClientOrganizationRequest(slug = slug)
        val dto = commonTestHelper.buildCreateClientOrganizationDto()
        val clientOrganization = commonTestHelper.buildClientOrganization()
        // expectations
        expect(clientOrganizationService.findBySlugAndOrganization(slug, request.organizationUuid)).andReturn(Optional.empty())
        expect(mapperFacade.map(request, CreateClientOrganizationDto::class.java)).andReturn(dto)
        expect(clientOrganizationService.create(dto)).andReturn(clientOrganization)
        replayAll()
        // test scenario
        val resultResponse = clientOrganizationServiceFacade.create(request)
        restHelper.assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().uuid).isEqualTo(clientOrganization.uuid)
        verifyAll()
    }
}