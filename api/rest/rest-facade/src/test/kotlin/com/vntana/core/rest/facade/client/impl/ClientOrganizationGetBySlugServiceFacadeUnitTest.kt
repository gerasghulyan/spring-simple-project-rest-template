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
class ClientOrganizationGetBySlugServiceFacadeUnitTest : AbstractClientOrganizationServiceFacadeUnitTest() {

    @Test
    fun `test getBySlug with illegal arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        restHelper.assertBasicErrorResultResponse(clientOrganizationServiceFacade.getBySlug(uuid(), null), ClientOrganizationErrorResponseModel.MISSING_SLUG)
        restHelper.assertBasicErrorResultResponse(clientOrganizationServiceFacade.getBySlug(uuid(), ""), ClientOrganizationErrorResponseModel.MISSING_SLUG)
        restHelper.assertBasicErrorResultResponse(clientOrganizationServiceFacade.getBySlug(null, uuid()), ClientOrganizationErrorResponseModel.MISSING_ORGANIZATION_UUID)
        restHelper.assertBasicErrorResultResponse(clientOrganizationServiceFacade.getBySlug("", uuid()), ClientOrganizationErrorResponseModel.MISSING_ORGANIZATION_UUID)
        verifyAll()
    }

    @Test
    fun `test getBySlug when not found`() {
        // test data
        resetAll()
        val slug = uuid()
        val organizationUuid = uuid()
        // expectations
        expect(organizationClientService.findBySlugAndOrganization(slug, organizationUuid)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        val resultResponse = clientOrganizationServiceFacade.getBySlug(organizationUuid, slug)
        restHelper.assertBasicErrorResultResponse(resultResponse, ClientOrganizationErrorResponseModel.CLIENT_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun test() {
        // test data
        resetAll()
        val clientOrganization = commonTestHelper.buildClientOrganization()
        // expectations
        expect(organizationClientService.findBySlugAndOrganization(clientOrganization.slug, clientOrganization.organization.uuid)).andReturn(Optional.of(clientOrganization))
        replayAll()
        // test scenario
        val resultResponse = clientOrganizationServiceFacade.getBySlug(clientOrganization.organization.uuid, clientOrganization.slug)
        restHelper.assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().clientUuid).isEqualTo(clientOrganization.uuid)
        assertThat(resultResponse.response().clientName).isEqualTo(clientOrganization.name)
        assertThat(resultResponse.response().organizationUuid).isEqualTo(clientOrganization.organization.uuid)
        assertThat(resultResponse.response().organizationSlug).isEqualTo(clientOrganization.organization.slug)
        assertThat(resultResponse.response().clientSlug).isEqualTo(clientOrganization.slug)
        verifyAll()
    }
}