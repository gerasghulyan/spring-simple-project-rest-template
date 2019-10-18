package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.model.user.response.UserCreateResultResponse
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 11:25 AM
 */
class UserCreateWebTest : AbstractUserWebTest() {

    override fun endpointMapping(): String = baseMapping() + "/create"

    @Test
    fun `test create with invalid arguments`() {
        val response1: ResponseEntity<UserCreateResultResponse> = testRestTemplate.postForEntity(endpointMapping(), restHelper.buildCreateUserRequest(clientName = null))
        restHelper.assertBasicErrorResultResponse(response1.body!!, UserErrorResponseModel.MISSING_CLIENT_NAME)
        val response2: ResponseEntity<UserCreateResultResponse> = testRestTemplate.postForEntity(endpointMapping(), restHelper.buildCreateUserRequest(clientSlug = null))
        restHelper.assertBasicErrorResultResponse(response2.body!!, UserErrorResponseModel.MISSING_CLIENT_SLUG)
        val response3: ResponseEntity<UserCreateResultResponse> = testRestTemplate.postForEntity(endpointMapping(), restHelper.buildCreateUserRequest(fullName = null))
        restHelper.assertBasicErrorResultResponse(response3.body!!, UserErrorResponseModel.MISSING_FULL_NAME)
        val response4: ResponseEntity<UserCreateResultResponse> = testRestTemplate.postForEntity(endpointMapping(), restHelper.buildCreateUserRequest(email = null))
        restHelper.assertBasicErrorResultResponse(response4.body!!, UserErrorResponseModel.MISSING_EMAIL)
        val response5: ResponseEntity<UserCreateResultResponse> = testRestTemplate.postForEntity(endpointMapping(), restHelper.buildCreateUserRequest(password = null))
        restHelper.assertBasicErrorResultResponse(response5.body!!, UserErrorResponseModel.MISSING_PASSWORD)
    }

    @Test
    fun `test create`() {
        val createRequest = restHelper.buildCreateUserRequest()
        val response: ResponseEntity<UserCreateResultResponse> = testRestTemplate.postForEntity(endpointMapping(), createRequest)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body!!.success()).isTrue()
        assertThat(response.body!!.response().getUuid()).isNotEmpty()
    }
}