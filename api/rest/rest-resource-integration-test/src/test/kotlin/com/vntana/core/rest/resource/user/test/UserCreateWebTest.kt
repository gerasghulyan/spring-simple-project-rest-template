package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.user.response.UserCreateResultResponse
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions
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
    fun `test create`() {
        val createRequest = userTestHelper.buildCreateUserRequest()
        val response: ResponseEntity<UserCreateResultResponse> = testRestTemplate.postForEntity(endpointMapping(), createRequest)
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(response.body!!.success()).isTrue()
        Assertions.assertThat(response.body!!.response().uuid()).isNotEmpty()
    }
}