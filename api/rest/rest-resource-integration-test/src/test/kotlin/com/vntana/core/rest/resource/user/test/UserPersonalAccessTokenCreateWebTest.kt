package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.model.user.request.CreatePersonalAccessTokenRequest
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 1:51 AM
 */
class UserPersonalAccessTokenCreateWebTest : AbstractUserWebTest() {
    
    @Test
    fun `test when missing token`() {
        assertBasicErrorResultResponse(
            userResourceClient.createPersonalAccessToken(CreatePersonalAccessTokenRequest(null, null)),
            UserErrorResponseModel.MISSING_TOKEN, UserErrorResponseModel.MISSING_UUID
        )
    }

    @Test
    fun `test when user not found`() {
        val token = uuid()
        val userUuid = uuid()
        assertBasicErrorResultResponse(
            userResourceClient.createPersonalAccessToken(CreatePersonalAccessTokenRequest(token, userUuid)),
            UserErrorResponseModel.USER_NOT_FOUND
        )
    }

    @Test
    fun test() {
        val token = uuid()
        val userUuid = resourceHelper.persistUser(
            createUserRequest = resourceHelper.buildCreateUserRequest()
        ).response().uuid
        userResourceClient.createPersonalAccessToken(CreatePersonalAccessTokenRequest(token, userUuid)).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body!!.response().token).isEqualTo(token)
            assertThat(it.body!!.response().userUuid).isEqualTo(userUuid)
        }
    }
}