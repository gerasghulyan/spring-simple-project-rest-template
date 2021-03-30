package com.vntana.core.rest.resource.auth.test

import com.vntana.core.model.security.request.CreatePersonalAccessTokenRequest
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.resource.auth.AbstractAuthWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 1:51 AM
 */
class AuthCreatePersonalAccessTokenWebTest : AbstractAuthWebTest() {

    @Test
    fun `test when missing token`() {
        assertBasicErrorResultResponse(
            authResourceClient.createPersonalAccessToken(
                CreatePersonalAccessTokenRequest(
                    null,
                    null
                )
            ),
            UserErrorResponseModel.MISSING_TOKEN, UserErrorResponseModel.MISSING_UUID
        )
    }

    @Test
    fun `test when user not found`() {
        val token = uuid()
        val userUuid = uuid()
        assertBasicErrorResultResponse(
            authResourceClient.createPersonalAccessToken(
                CreatePersonalAccessTokenRequest(
                    token,
                    userUuid
                )
            ),
            UserErrorResponseModel.USER_NOT_FOUND
        )
    }

    @Test
    fun test() {
        val token = uuid()
        val userUuid = userResourceTestHelper.persistUser(
            createUserRequest = userResourceTestHelper.buildCreateUserRequest()
        ).response().uuid
        authResourceClient.createPersonalAccessToken(
            CreatePersonalAccessTokenRequest(
                token,
                userUuid
            )
        ).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().token).isEqualTo(token)
            assertThat(it.response().userUuid).isEqualTo(userUuid)
        }
    }
}