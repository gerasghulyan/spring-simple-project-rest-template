package com.vntana.core.rest.facade.client.impl

import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel
import com.vntana.core.rest.facade.client.AbstractClientOrganizationServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan.
 * Date: 10/9/19
 * Time: 3:54 PM
 */
class ClientOrganizationGetByUuidServiceFacadeUnitTest : AbstractClientOrganizationServiceFacadeUnitTest() {

    @Test
    fun `test getByUuid with illegal arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        restHelper.assertBasicErrorResultResponse(clientOrganizationServiceFacade.getByUuid(null), ClientOrganizationErrorResponseModel.MISSING_UUID)
        restHelper.assertBasicErrorResultResponse(clientOrganizationServiceFacade.getByUuid(""), ClientOrganizationErrorResponseModel.MISSING_UUID)
        verifyAll()
    }

    @Test
    fun `test getByUuid when not found`() {
        // test data
        resetAll()
        val uuid = uuid()
        // expectations
        expect(clientOrganizationService.findByUuid(uuid)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        val resultResponse = clientOrganizationServiceFacade.getByUuid(uuid)
        restHelper.assertBasicErrorResultResponse(resultResponse, ClientOrganizationErrorResponseModel.CLIENT_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        // test data
        resetAll()
        val clientOrganization = commonTestHelper.buildClientOrganization()
        // expectations
        expect(clientOrganizationService.findByUuid(clientOrganization.uuid)).andReturn(Optional.of(clientOrganization))
        replayAll()
        // test scenario
        val resultResponse = clientOrganizationServiceFacade.getByUuid(clientOrganization.uuid)
        restHelper.assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().clientUuid).isEqualTo(clientOrganization.uuid)
        assertThat(resultResponse.response().clientName).isEqualTo(clientOrganization.name)
        assertThat(resultResponse.response().organizationUuid).isEqualTo(clientOrganization.organization.uuid)
        assertThat(resultResponse.response().organizationSlug).isEqualTo(clientOrganization.organization.slug)
        assertThat(resultResponse.response().clientSlug).isEqualTo(clientOrganization.slug)
        verifyAll()
    }
}