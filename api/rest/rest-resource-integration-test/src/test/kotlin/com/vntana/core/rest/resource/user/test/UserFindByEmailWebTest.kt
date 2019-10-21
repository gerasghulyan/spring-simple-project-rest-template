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
        val response1 = userResourceClient.findByEmail(restHelper.buildFindUserByEmailRequest(email = null))
        restHelper.assertBasicErrorResultResponse(response1, UserErrorResponseModel.MISSING_EMAIL)
    }

    @Test
    fun `test findByEmail`() {
        val email = uuid()
        val createRequest = restHelper.buildCreateUserRequest(email = email)
        userResourceClient.createUser(createRequest)
        val findUserByEmailRequest = restHelper.buildFindUserByEmailRequest(email = email)
        val response = userResourceClient.findByEmail(findUserByEmailRequest)
        restHelper.assertBasicSuccessResultResponse(response)
        assertThat(response.response().isExists).isTrue()
    }
}