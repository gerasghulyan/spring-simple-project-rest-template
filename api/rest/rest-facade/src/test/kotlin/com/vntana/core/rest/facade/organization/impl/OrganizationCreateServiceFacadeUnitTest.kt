package com.vntana.core.rest.facade.organization.impl

import com.vntana.core.model.organization.error.OrganizationErrorResponseModel
import com.vntana.core.rest.facade.organization.AbstractOrganizationServiceFacadeUnitTest
import com.vntana.core.service.organization.dto.CreateOrganizationDto
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/10/19
 * Time: 11:35 AM
 */
class OrganizationCreateServiceFacadeUnitTest : AbstractOrganizationServiceFacadeUnitTest() {
    @Test
    fun `test create when organization already exists for slug`() {
        // test data
        resetAll()
        val slug = uuid()
        val request = restHelper.buildCreateOrganizationRequest(slug = slug)
        val organization = commonTestHelper.buildOrganization()
        // expectations
        expect(organizationService.findBySlug(slug)).andReturn(Optional.of(organization))
        replayAll()
        // test scenario
        val resultResponse = organizationServiceFacade.create(request)
        restHelper.assertBasicErrorResultResponse(resultResponse, OrganizationErrorResponseModel.SLUG_ALREADY_EXISTS)
        verifyAll()
    }

    @Test
    fun `test create`() {
        // test data
        resetAll()
        val slug = uuid()
        val request = restHelper.buildCreateOrganizationRequest(slug = slug)
        val dto = commonTestHelper.buildCreateOrganizationDto()
        val organization = commonTestHelper.buildOrganization()
        // expectations
        expect(organizationService.findBySlug(slug)).andReturn(Optional.empty())
        expect(mapperFacade.map(request, CreateOrganizationDto::class.java)).andReturn(dto)
        expect(organizationService.create(dto)).andReturn(organization)
        expect(organizationLifecycleMediator.onCreated(organization))
        replayAll()
        // test scenario
        val resultResponse = organizationServiceFacade.create(request)
        restHelper.assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().uuid).isEqualTo(organization.uuid)
        verifyAll()
    }
}