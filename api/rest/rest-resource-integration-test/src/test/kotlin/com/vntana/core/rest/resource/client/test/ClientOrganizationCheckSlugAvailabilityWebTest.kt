package com.vntana.core.rest.resource.client.test

import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel
import com.vntana.core.model.client.response.CheckAvailableClientOrganizationSlugResultResponse
import com.vntana.core.rest.resource.client.AbstractClientOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

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
        val response: ResponseEntity<CheckAvailableClientOrganizationSlugResultResponse> = testRestTemplate.postForEntity(endpointMapping(), request)
        restTestHelper.assertBasicErrorResultResponse(response.body!!, ClientOrganizationErrorResponseModel.MISSING_SLUG)
    }

    @Test
    fun `test checkSlugAvailability`() {
        val request = restTestHelper.buildCheckAvailableClientOrganizationSlugRequest()
        val response: ResponseEntity<CheckAvailableClientOrganizationSlugResultResponse> = testRestTemplate.postForEntity(endpointMapping(), request)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body!!.success()).isTrue()
        assertThat(response.body!!.response().isAvailable).isTrue()
        assertThat(response.body!!.response().suggested).isEqualTo(request.slug)
    }
}