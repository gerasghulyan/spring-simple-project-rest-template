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
    fun `test checkSlugAvailability with invalid argumments`() {
        val request = restHelper.buildCheckAvailableClientOrganizationSlugRequest(slug = null)
        val response = clientOrganizationResourceClient.checkSlugAvailability(request)
        restHelper.assertBasicErrorResultResponse(response, ClientOrganizationErrorResponseModel.MISSING_SLUG)
    }

    @Test
    fun `test checkSlugAvailability`() {
        val request = restHelper.buildCheckAvailableClientOrganizationSlugRequest()
        val response = clientOrganizationResourceClient.checkSlugAvailability(request)
        restHelper.assertBasicSuccessResultResponse(response)
        assertThat(response.response().isAvailable).isTrue()
        assertThat(response.response().suggested).isEqualTo(request.slug)
    }
}