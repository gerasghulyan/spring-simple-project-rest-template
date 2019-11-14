package com.vntana.core.rest.resource.client.test

import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel
import com.vntana.core.rest.resource.client.AbstractClientOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 5:53 PM
 */
class ClientOrganizationCheckSlugAvailabilityWebTest : AbstractClientOrganizationWebTest() {

    @Test
    fun `test checkSlugAvailability with invalid arguments`() {
        clientOrganizationResourceClient.checkSlugAvailability(clientOrganizationResourceTestHelper.buildCheckAvailableClientOrganizationSlugRequest(slug = null)).let {
            assertBasicErrorResultResponse(it, ClientOrganizationErrorResponseModel.MISSING_SLUG)
        }
        clientOrganizationResourceClient.checkSlugAvailability(clientOrganizationResourceTestHelper.buildCheckAvailableClientOrganizationSlugRequest(slug = "")).let {
            assertBasicErrorResultResponse(it, ClientOrganizationErrorResponseModel.MISSING_SLUG)
        }
        clientOrganizationResourceClient.checkSlugAvailability(clientOrganizationResourceTestHelper.buildCheckAvailableClientOrganizationSlugRequest(organizationUuid = null)).let {
            assertBasicErrorResultResponse(it, ClientOrganizationErrorResponseModel.MISSING_ORGANIZATION_UUID)
        }
        clientOrganizationResourceClient.checkSlugAvailability(clientOrganizationResourceTestHelper.buildCheckAvailableClientOrganizationSlugRequest(organizationUuid = "")).let {
            assertBasicErrorResultResponse(it, ClientOrganizationErrorResponseModel.MISSING_ORGANIZATION_UUID)
        }
    }

    @Test
    fun `test checkSlugAvailability when organization not found`() {
        val request = clientOrganizationResourceTestHelper.buildCheckAvailableClientOrganizationSlugRequest()
        val response = clientOrganizationResourceClient.checkSlugAvailability(request)
        clientOrganizationResourceTestHelper.assertBasicSuccessResultResponse(response)
        assertThat(response.response().isAvailable).isTrue()
        assertThat(response.response().suggested).isEqualTo(request.slug)
    }

    @Test
    fun `test checkSlugAvailability when available`() {
        val request = clientOrganizationResourceTestHelper.buildCheckAvailableClientOrganizationSlugRequest()
        val response = clientOrganizationResourceClient.checkSlugAvailability(request)
        clientOrganizationResourceTestHelper.assertBasicSuccessResultResponse(response)
        assertThat(response.response().isAvailable).isTrue()
        assertThat(response.response().suggested).isEqualTo(request.slug)
    }

    @Test
    fun `test checkSlugAvailability when not available`() {
        val slugName = uuid()
        val suggestedSlugName = "${slugName}1"
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid, slug = slugName)
        val request = clientOrganizationResourceTestHelper.buildCheckAvailableClientOrganizationSlugRequest(slug = slugName, organizationUuid = organizationUuid)
        val response = clientOrganizationResourceClient.checkSlugAvailability(request)
        clientOrganizationResourceTestHelper.assertBasicSuccessResultResponse(response)
        assertThat(response.response().isAvailable).isFalse()
        assertThat(response.response().suggested).isEqualTo(suggestedSlugName)
    }
}