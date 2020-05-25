package com.vntana.core.rest.facade.organization.impl

import com.vntana.core.model.organization.error.OrganizationErrorResponseModel
import com.vntana.core.rest.facade.organization.AbstractOrganizationServiceFacadeUnitTest
import com.vntana.core.service.organization.dto.UpdateOrganizationDto
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 4:13 PM
 */
class OrganizationUpdateServiceFacadeUnitTest : AbstractOrganizationServiceFacadeUnitTest() {

    @Test
    fun `test when organization not found`() {
        val request = restHelper.buildUpdateOrganizationRequest()
        resetAll()
        expect(organizationService.existsByUuid(request.uuid)).andReturn(false)
        replayAll()
        assertBasicErrorResultResponse(organizationServiceFacade.update(request), OrganizationErrorResponseModel.ORGANIZATION_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        val request = restHelper.buildUpdateOrganizationRequest()
        val dto = commonTestHelper.buildUpdateOrganizationDto(uuid = request.uuid, imageBlobId = request.imageBlobId, name = request.name)
        val updatedOrganization = commonTestHelper.buildOrganization()
        resetAll()
        expect(organizationService.existsByUuid(request.uuid)).andReturn(true)
        expect(mapperFacade.map(request, UpdateOrganizationDto::class.java)).andReturn(dto)
        expect(organizationService.update(dto)).andReturn(updatedOrganization)
        expect(organizationLifecycleMediator.onUpdated(updatedOrganization)).andVoid()
        expect(organizationUuidAwareLifecycleMediator.onUpdated(updatedOrganization.uuid)).andVoid()
        replayAll()
        organizationServiceFacade.update(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().uuid).isEqualTo(updatedOrganization.uuid)
        }
        verifyAll()
    }
}