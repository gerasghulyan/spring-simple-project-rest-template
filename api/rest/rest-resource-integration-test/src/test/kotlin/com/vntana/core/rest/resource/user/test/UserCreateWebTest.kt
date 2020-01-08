package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 11:25 AM
 */
class UserCreateWebTest : AbstractUserWebTest() {

    @Test
    fun `test create with invalid arguments`() {
        val response1 = userResourceClient.createUser(resourceHelper.buildCreateUserRequest(clientName = null))
        resourceHelper.assertBasicErrorResultResponse(response1, UserErrorResponseModel.MISSING_CLIENT_NAME)
        val response2 = userResourceClient.createUser(resourceHelper.buildCreateUserRequest(clientSlug = null))
        resourceHelper.assertBasicErrorResultResponse(response2, UserErrorResponseModel.MISSING_CLIENT_SLUG)
        val response3 = userResourceClient.createUser(resourceHelper.buildCreateUserRequest(fullName = null))
        resourceHelper.assertBasicErrorResultResponse(response3, UserErrorResponseModel.MISSING_FULL_NAME)
        val response4 = userResourceClient.createUser(resourceHelper.buildCreateUserRequest(email = null))
        resourceHelper.assertBasicErrorResultResponse(response4, UserErrorResponseModel.MISSING_EMAIL)
        val response5 = userResourceClient.createUser(resourceHelper.buildCreateUserRequest(password = null))
        resourceHelper.assertBasicErrorResultResponse(response5, UserErrorResponseModel.MISSING_PASSWORD)
        val response6 = userResourceClient.createUser(resourceHelper.buildCreateUserRequest(token = null))
        resourceHelper.assertBasicErrorResultResponse(response6, UserErrorResponseModel.MISSING_VERIFICATION_TOKEN)
    }

    @Test
    fun `test create`() {
        val email = resourceHelper.email()
        val response = userResourceClient.createUser(resourceHelper.buildCreateUserRequest(email = email))
        resourceHelper.assertBasicSuccessResultResponse(response)
        assertThat(response.response().uuid).isNotEmpty()
        assertThat(response.response().organizationUuid).isNotEmpty()
        verify(customerResourceClient, times(1))
                .create(ArgumentMatchers.argThat { request -> request.email == email })
    }

    @Test
    fun `test create with existing email`() {
        // persist user
        val userRequest = resourceHelper.buildCreateUserRequest()
        assertThat(resourceHelper.persistUser(userRequest).response().uuid).isNotEmpty()
        // retry to persist existing user
        resourceHelper.assertBasicErrorResultResponse(userResourceClient.createUser(userRequest), UserErrorResponseModel.SIGN_UP_WITH_EXISTING_EMAIL)
    }

    @Test
    fun `test create user by invalid email`() {
        val userRequest = resourceHelper.buildCreateUserRequest(email = resourceHelper.buildUserInvalidEmail())
        resourceHelper.assertBasicErrorResultResponse(userResourceClient.createUser(userRequest), UserErrorResponseModel.INVALID_EMAIL_FORMAT)
    }
}