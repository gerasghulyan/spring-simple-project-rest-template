package com.vntana.core.rest.facade.organization.impl

import com.vntana.core.model.organization.error.OrganizationErrorResponseModel
import com.vntana.core.model.organization.response.get.model.OrganizationStatusModel
import com.vntana.core.rest.facade.organization.AbstractOrganizationServiceFacadeUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Geras Ghulyan.
 * Date: 10/9/19
 * Time: 3:54 PM
 */
class OrganizationGetByUuidServiceFacadeUnitTest : AbstractOrganizationServiceFacadeUnitTest() {

    @Test
    fun `test getByUuid with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        restHelper.assertBasicErrorResultResponse(organizationServiceFacade.getByUuid(null), OrganizationErrorResponseModel.MISSING_UUID)
        restHelper.assertBasicErrorResultResponse(organizationServiceFacade.getByUuid(""), OrganizationErrorResponseModel.MISSING_UUID)
        verifyAll()
    }

    @Test
    fun `test getByUuid`() {
        // test data
        resetAll()
        val ownerEmail = uuid()
        val organization = commonTestHelper.buildOrganization()
        // expectations
        expect(organizationService.existsByUuid(organization.uuid)).andReturn(true)
        expect(organizationService.getByUuid(organization.uuid)).andReturn(organization)
        expect(organizationService.getOrganizationOwnerEmail(organization.uuid)).andReturn(ownerEmail)
        replayAll()
        // test scenario
        val resultResponse = organizationServiceFacade.getByUuid(organization.uuid)
        restHelper.assertBasicSuccessResultResponse(resultResponse)
        assertThat(resultResponse.response().uuid).isEqualTo(organization.uuid)
        assertThat(resultResponse.response().name).isEqualTo(organization.name)
        assertThat(resultResponse.response().slug).isEqualTo(organization.slug)
        assertThat(resultResponse.response().imageBlobId).isEqualTo(organization.imageBlobId)
        assertThat(resultResponse.response().status).isEqualTo(OrganizationStatusModel.valueOf(organization.status.name))
        assertThat(resultResponse.response().created).isEqualTo(organization.created)
        assertThat(resultResponse.response().email).isEqualTo(ownerEmail)
        verifyAll()
    }

    @Test
    fun `test getByUuid when not found`() {
        // test data
        resetAll()
        val organization = commonTestHelper.buildOrganization()
        // expectations
        expect(organizationService.existsByUuid(organization.uuid)).andReturn(false)
        replayAll()
        // test scenario
        val resultResponse = organizationServiceFacade.getByUuid(organization.uuid)
        restHelper.assertBasicErrorResultResponse(resultResponse, OrganizationErrorResponseModel.ORGANIZATION_NOT_FOUND)
        verifyAll()
    }
}