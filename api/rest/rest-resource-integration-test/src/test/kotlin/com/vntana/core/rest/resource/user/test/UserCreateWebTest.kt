package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 11:25 AM
 */
class UserCreateWebTest : AbstractUserWebTest() {

    @Test
    fun `test create with invalid arguments`() {
        val response1 = userResourceClient.createUser(restHelper.buildCreateUserRequest(clientName = null))
        restHelper.assertBasicErrorResultResponse(response1, UserErrorResponseModel.MISSING_CLIENT_NAME)
        val response2 = userResourceClient.createUser(restHelper.buildCreateUserRequest(clientSlug = null))
        restHelper.assertBasicErrorResultResponse(response2, UserErrorResponseModel.MISSING_CLIENT_SLUG)
        val response3 = userResourceClient.createUser(restHelper.buildCreateUserRequest(fullName = null))
        restHelper.assertBasicErrorResultResponse(response3, UserErrorResponseModel.MISSING_FULL_NAME)
        val response4 = userResourceClient.createUser(restHelper.buildCreateUserRequest(email = null))
        restHelper.assertBasicErrorResultResponse(response4, UserErrorResponseModel.MISSING_EMAIL)
        val response5 = userResourceClient.createUser(restHelper.buildCreateUserRequest(password = null))
        restHelper.assertBasicErrorResultResponse(response5, UserErrorResponseModel.MISSING_PASSWORD)
    }

    @Test
    fun `test create`() {
        val createRequest = restHelper.buildCreateUserRequest()
        val response = userResourceClient.createUser(createRequest)
        restHelper.assertBasicSuccessResultResponse(response)
        assertThat(response.response().uuid).isNotEmpty()
    }
}