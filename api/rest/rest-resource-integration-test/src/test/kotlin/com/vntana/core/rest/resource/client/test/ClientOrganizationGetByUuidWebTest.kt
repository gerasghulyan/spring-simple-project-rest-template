package com.vntana.core.rest.resource.client.test

import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel
import com.vntana.core.rest.resource.client.AbstractClientOrganizationWebTest
import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 11/11/2019
 * Time: 9:22 PM
 */
class ClientOrganizationGetByUuidWebTest : AbstractClientOrganizationWebTest() {
    @Test
    fun `test getByUuid with invalid arguments`() {
        assertBasicErrorResultResponse(clientOrganizationResourceClient.getByUuid(" "), ClientOrganizationErrorResponseModel.MISSING_UUID)
    }

    @Test
    fun `test getByUuid`() {
        // given
        val slugName = uuid()
        val clientName = uuid()
        val organizationSlug = uuid()
        val imageId = uuid()
        val organizationUuid = organizationResourceTestHelper.persistOrganization(slug = organizationSlug).response().uuid
        val client = clientOrganizationResourceTestHelper.persistClientOrganization(
                organizationUuid = organizationUuid,
                name = clientName,
                slug = slugName,
                imageId = imageId
        )
        // when
        val response = clientOrganizationResourceClient.getByUuid(client.response().uuid)
        // then
        assertBasicSuccessResultResponse(response)
        Assertions.assertThat(response.response().clientUuid).isEqualTo(client.response().uuid)
        Assertions.assertThat(response.response().clientName).isEqualTo(clientName)
        Assertions.assertThat(response.response().clientSlug).isEqualTo(slugName)
        Assertions.assertThat(response.response().organizationUuid).isEqualTo(organizationUuid)
        Assertions.assertThat(response.response().organizationSlug).isEqualTo(organizationSlug)
        Assertions.assertThat(response.response().imageId).isEqualTo(imageId)
    }
}