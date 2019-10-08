package com.vntana.core.rest.resource.user.test

import com.vntana.core.models.user.request.UserCreateResultResponseModel
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
class UserCreateTest : AbstractUserWebTest() {

    override fun endpointMapping(): String = baseMapping() + "/create"

    @Test
    fun `test`() {
        val createRequest = userHelper.buildCreateUserRequest()
        val response: ResponseEntity<UserCreateResultResponseModel> = testRestTemplate.postForEntity(endpointMapping(), createRequest)
        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(response.body.success()).isTrue()
        Assertions.assertThat(response.body.response().uuid()).isNotEmpty()
    }
}