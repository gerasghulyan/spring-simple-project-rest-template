package com.vntana.core.rest.facade.client.impl

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
class CheckSlugAvailabilityClientOrganizationServiceFacadeImplTest : AbstractClientOrganizationServiceFacadeUnitTest() {
    @Test
    fun `test checkSlugAvailability when is available at first iteration`() {
        // test data
        resetAll()
        val request = restHelper.buildCheckAvailableClientOrganizationSlugRequest()
        // expectations
        expect(clientOrganizationService.findBySlug(request.slug)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        val resultResponse = clientOrganizationServiceFacade.checkSlugAvailability(request)
        assertBasicResultResponse(resultResponse)
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
        expect(clientOrganizationService.findBySlug(slug)).andReturn(Optional.of(clientOrganization))
        expect(clientOrganizationService.findBySlug("${slug}1")).andReturn(Optional.of(clientOrganization))
        expect(clientOrganizationService.findBySlug("${slug}2")).andReturn(Optional.of(clientOrganization))
        expect(clientOrganizationService.findBySlug("${slug}3")).andReturn(Optional.empty())
        replayAll()
        // test scenario
        val resultResponse = clientOrganizationServiceFacade.checkSlugAvailability(request)
        assertBasicResultResponse(resultResponse)
        assertThat(resultResponse.response().isAvailable).isFalse()
        assertThat(resultResponse.response().suggested).isEqualTo("${slug}3")
        verifyAll()
    }
}