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
    override fun endpointMapping(): String = baseMapping() + "/slug-availability"

    @Test
    fun `test checkSlugAvailability when slug is missing`() {
        val request = restTestHelper.buildCheckAvailableClientOrganizationSlugRequest(slug = null)
        val response = clientOrganizationResourceClient.checkSlugAvailability(request)
        restTestHelper.assertBasicErrorResultResponse(response, ClientOrganizationErrorResponseModel.MISSING_SLUG)
    }

    @Test
    fun `test checkSlugAvailability`() {
        val request = restTestHelper.buildCheckAvailableClientOrganizationSlugRequest()
        val response = clientOrganizationResourceClient.checkSlugAvailability(request)
        assertThat(response.success()).isTrue()
        assertThat(response.response().isAvailable).isTrue()
        assertThat(response.response().suggested).isEqualTo(request.slug)
    }
}