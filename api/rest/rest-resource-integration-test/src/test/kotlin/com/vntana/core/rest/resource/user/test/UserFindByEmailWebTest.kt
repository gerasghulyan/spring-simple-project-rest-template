package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.model.user.response.CreateUserResultResponse
import com.vntana.core.model.user.response.FindUserByEmailResultResponse
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.ResponseEntity

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 11:25 AM
 */
class UserFindByEmailWebTest : AbstractUserWebTest() {
    override fun endpointMapping(): String = baseMapping() + "/by-email"

    @Test
    fun `test with invalid arguments`() {
        val response1: ResponseEntity<FindUserByEmailResultResponse> = testRestTemplate.postForEntity(endpointMapping(), restHelper.buildFindUserByEmailRequest(email = null))
        restHelper.assertBasicErrorResultResponse(response1.body!!, UserErrorResponseModel.MISSING_EMAIL)
    }

    @Test
    fun `test findByEmail`() {
        val email = uuid()
        val createRequest = restHelper.buildCreateUserRequest(email = email)
        testRestTemplate.postForEntity<CreateUserResultResponse>(baseMapping() + "/create", createRequest)
        val findUserByEmailRequest = restHelper.buildFindUserByEmailRequest(email = email)
        val response: ResponseEntity<FindUserByEmailResultResponse> = testRestTemplate.postForEntity(endpointMapping(), findUserByEmailRequest)
        restHelper.assertBasicSuccessResultResponse(response.body!!)
        assertThat(response.body!!.response().isExists).isTrue()
    }
}