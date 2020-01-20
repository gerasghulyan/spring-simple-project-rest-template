package com.vntana.core.rest.resource.client.test

import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel
import com.vntana.core.rest.resource.client.AbstractClientOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2019
 * Time: 9:22 PM
 */
class ClientOrganizationGetBySlugWebTest : AbstractClientOrganizationWebTest() {
    @Test
    fun `test getBySlug with invalid arguments`() {
        assertBasicErrorResultResponse(clientOrganizationResourceClient.getBySlug(" ", uuid()), ClientOrganizationErrorResponseModel.MISSING_ORGANIZATION_UUID)
        assertBasicErrorResultResponse(clientOrganizationResourceClient.getBySlug(uuid(), " "), ClientOrganizationErrorResponseModel.MISSING_SLUG)
    }

    @Test
    fun `test getBySlug when not found`() {
        val response = clientOrganizationResourceClient.getBySlug(uuid(), uuid())
        assertBasicErrorResultResponse(response, ClientOrganizationErrorResponseModel.CLIENT_NOT_FOUND)
    }

    @Test
    fun `test getBySlug`() {
        // given
        val slugName = uuid()
        val clientName = uuid()
        val organizationSlug = uuid()
        val imageBlobId = uuid()
        val organizationUuid = organizationResourceTestHelper.persistOrganization(slug = organizationSlug).response().uuid
        val client = clientOrganizationResourceTestHelper.persistClientOrganization(
                organizationUuid = organizationUuid,
                name = clientName,
                slug = slugName,
                imageBlobId = imageBlobId
        )
        // when
        val response = clientOrganizationResourceClient.getBySlug(organizationUuid, slugName)
        // then
        assertBasicSuccessResultResponse(response)
        assertThat(response.response().clientUuid).isEqualTo(client.response().uuid)
        assertThat(response.response().clientName).isEqualTo(clientName)
        assertThat(response.response().clientSlug).isEqualTo(slugName)
        assertThat(response.response().organizationUuid).isEqualTo(organizationUuid)
        assertThat(response.response().organizationSlug).isEqualTo(organizationSlug)
        assertThat(response.response().imageBlobId).isEqualTo(imageBlobId)
    }
}