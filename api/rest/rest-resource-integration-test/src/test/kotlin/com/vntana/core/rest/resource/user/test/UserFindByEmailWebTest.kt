package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 11:25 AM
 */
class UserFindByEmailWebTest : AbstractUserWebTest() {

    @Test
    fun `test with invalid arguments`() {
        val response1 = userResourceClient.findByEmail(resourceHelper.buildFindUserByEmailRequest(email = null))
        resourceHelper.assertBasicErrorResultResponse(response1, UserErrorResponseModel.MISSING_EMAIL)
    }

    @Test
    fun `test findByEmail`() {
        val createRequest = resourceHelper.buildCreateUserRequest()
        val userUuid = userResourceClient.createUser(createRequest).response().uuid
        val findUserByEmailRequest = resourceHelper.buildFindUserByEmailRequest(email = createRequest.email)
        val response = userResourceClient.findByEmail(findUserByEmailRequest)
        resourceHelper.assertBasicSuccessResultResponse(response)
        assertThat(response.response().isExists).isTrue()
        assertThat(response.response().uuid).isEqualTo(userUuid)
    }
}