package com.vntana.core.rest.facade.organization.impl

import com.vntana.core.rest.facade.organization.AbstractOrganizationServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 3:54 PM
 */
class OrganizationCheckSlugAvailabilityServiceFacadeUnitTest : AbstractOrganizationServiceFacadeUnitTest() {
    @Test
    fun `test checkSlugAvailability when is available at first iteration`() {
        // test data
        resetAll()
        val request = restHelper.buildCheckAvailableOrganizationSlugRequest()
        // expectations
        expect(organizationService.findBySlug(request.slug)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        val resultResponse = organizationServiceFacade.checkSlugAvailability(request)
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
        val organization = commonTestHelper.buildOrganization()
        val request = restHelper.buildCheckAvailableOrganizationSlugRequest(slug = slug)
        // expectations
        expect(organizationService.findBySlug(slug)).andReturn(Optional.of(organization))
        expect(organizationService.findBySlug("${slug}1")).andReturn(Optional.of(organization))
        expect(organizationService.findBySlug("${slug}2")).andReturn(Optional.of(organization))
        expect(organizationService.findBySlug("${slug}3")).andReturn(Optional.empty())
        replayAll()
        // test scenario
        val resultResponse = organizationServiceFacade.checkSlugAvailability(request)
        restHelper.assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().isAvailable).isFalse()
        assertThat(resultResponse.response().suggested).isEqualTo("${slug}3")
        verifyAll()
    }
}