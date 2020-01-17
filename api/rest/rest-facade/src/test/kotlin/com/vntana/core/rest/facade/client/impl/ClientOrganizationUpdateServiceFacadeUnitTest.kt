package com.vntana.core.rest.facade.client.impl

import com.vntana.core.rest.facade.client.AbstractClientOrganizationServiceFacadeUnitTest
import com.vntana.core.service.client.dto.UpdateClientOrganizationDto
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Geras Ghulyan.
 * Date: 10/10/19
 * Time: 11:35 AM
 */
class ClientOrganizationUpdateServiceFacadeUnitTest : AbstractClientOrganizationServiceFacadeUnitTest() {

    @Test
    fun `test update`() {
        // test data
        resetAll()
        val request = restHelper.buildUpdateClientOrganizationRequest()
        val dto = commonTestHelper.buildUpdateClientOrganizationDto()
        val clientOrganization = commonTestHelper.buildClientOrganization()
        // expectations
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