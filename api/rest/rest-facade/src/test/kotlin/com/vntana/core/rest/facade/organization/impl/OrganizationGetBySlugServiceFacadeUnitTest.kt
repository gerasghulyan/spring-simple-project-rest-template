package com.vntana.core.rest.facade.organization.impl

import com.vntana.core.model.organization.error.OrganizationErrorResponseModel
import com.vntana.core.rest.facade.organization.AbstractOrganizationServiceFacadeUnitTest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan.
 * Date: 10/9/19
 * Time: 3:54 PM
 */
class OrganizationGetBySlugServiceFacadeUnitTest : AbstractOrganizationServiceFacadeUnitTest() {

    @Test
    fun `test getBySlug with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        restHelper.assertBasicErrorResultResponse( organizationServiceFacade.getBySlug(null), OrganizationErrorResponseModel.MISSING_SLUG)
        restHelper.assertBasicErrorResultResponse( organizationServiceFacade.getBySlug(""), OrganizationErrorResponseModel.MISSING_SLUG)
        verifyAll()
    }

    @Test
    fun `test getBySlug when not found`() {
        // test data
        resetAll()
        val slug = uuid()
        // expectations
        expect(organizationService.findBySlug(slug)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        val resultResponse = organizationServiceFacade.getBySlug(slug)
        restHelper.assertBasicErrorResultResponse(resultResponse, OrganizationErrorResponseModel.SLUG_NOT_FOUND)
        verifyAll()
    }

    @Test
    fun `test getBySlug`() {
        // test data
        resetAll()
        val organization = commonTestHelper.buildOrganization()
        // expectations
        expect(organizationService.findBySlug(organization.slug)).andReturn(Optional.of(organization))
        replayAll()
        // test scenario
        val resultResponse = organizationServiceFacade.getBySlug(organization.slug)
        restHelper.assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().uuid).isEqualTo(organization.uuid)
        assertThat(resultResponse.response().name).isEqualTo(organization.name)
        assertThat(resultResponse.response().slug).isEqualTo(organization.slug)
        assertThat(resultResponse.response().imageBlobId).isEqualTo(organization.imageBlobId)
        verifyAll()
    }
}