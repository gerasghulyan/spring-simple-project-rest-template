package com.vntana.core.rest.resource.client.test

import com.vntana.core.model.client.error.ClientOrganizationErrorResponseModel
import com.vntana.core.model.client.response.CreateClientOrganizationResultResponse
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
class CreateClientOrganizationWebTest : AbstractClientOrganizationWebTest() {
    override fun endpointMapping(): String = baseMapping() + "/create"

    @Test
    fun `test create when slug is missing`() {
        val request = restTestHelper.buildCreateClientOrganizationRequest(slug = null)
        val response: ResponseEntity<CreateClientOrganizationResultResponse> = testRestTemplate.postForEntity(endpointMapping(), request)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body!!.success()).isFalse()
        assertThat(response.body!!.errors()).contains(ClientOrganizationErrorResponseModel.MISSING_SLUG)
    }

    @Test
    fun `test create when name is missing`() {
        val request = restTestHelper.buildCreateClientOrganizationRequest(name = null)
        val response: ResponseEntity<CreateClientOrganizationResultResponse> = testRestTemplate.postForEntity(endpointMapping(), request)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body!!.success()).isFalse()
        assertThat(response.body!!.errors()).contains(ClientOrganizationErrorResponseModel.MISSING_NAME)
    }

    @Test
    fun `test create`() {
        val request = restTestHelper.buildCreateClientOrganizationRequest()
        val response: ResponseEntity<CreateClientOrganizationResultResponse> = testRestTemplate.postForEntity(endpointMapping(), request)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body!!.success()).isTrue()
        assertThat(response.body!!.response().uuid()).isNotBlank()
    }
}