package com.vntana.core.rest.resource.organization.test

import com.vntana.core.model.organization.error.OrganizationErrorResponseModel
import com.vntana.core.model.organization.response.CreateOrganizationResultResponse
import com.vntana.core.rest.resource.organization.AbstractOrganizationWebTest
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
class OrganizationCreateWebTest : AbstractOrganizationWebTest() {

    override fun endpointMapping(): String = baseMapping() + "/create"

    @Test
    fun `test create with invalid arguments`() {
        val response1: ResponseEntity<CreateOrganizationResultResponse> = testRestTemplate.postForEntity(
                endpointMapping(),
                restTestHelper.buildCreateOrganizationRequest(slug = null)
        )
        restTestHelper.assertBasicErrorResultResponse(response1.body!!, OrganizationErrorResponseModel.MISSING_SLUG)
        val response2: ResponseEntity<CreateOrganizationResultResponse> = testRestTemplate.postForEntity(
                endpointMapping(),
                restTestHelper.buildCreateOrganizationRequest(name = null)
        )
        restTestHelper.assertBasicErrorResultResponse(response2.body!!, OrganizationErrorResponseModel.MISSING_NAME)
    }

    @Test
    fun `test create`() {
        val request = restTestHelper.buildCreateOrganizationRequest()
        val response: ResponseEntity<CreateOrganizationResultResponse> = testRestTemplate.postForEntity(endpointMapping(), request)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body!!.success()).isTrue()
        assertThat(response.body!!.response().uuid()).isNotBlank()
    }
}