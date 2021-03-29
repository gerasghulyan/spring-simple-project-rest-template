package com.vntana.core.rest.resource.auth.test

import com.vntana.core.model.security.request.CreatePersonalAccessTokenRequest
import com.vntana.core.rest.resource.auth.AbstractAuthWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 4:14 PM
 */
class AuthExpirePersonalAccessTokenWebTest : AbstractAuthWebTest() {

    @Test
    fun `test when token does not exist`() {
        val userUuid = userResourceTestHelper.persistUser(
            createUserRequest = userResourceTestHelper.buildCreateUserRequest()
        ).response().uuid
        authResourceClient.expirePersonalAccessTokenByUserUuid(userUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userUuid).isEqualTo(userUuid)
        }
    }

    @Test
    fun `test when token exists`() {
        val userUuid = userResourceTestHelper.persistUser(
            createUserRequest = userResourceTestHelper.buildCreateUserRequest()
        ).response().uuid
        val token = uuid()
        authResourceClient.createPersonalAccessToken(
            CreatePersonalAccessTokenRequest(
                token,
                userUuid
            )
        )
        authResourceClient.expirePersonalAccessTokenByUserUuid(userUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().userUuid).isEqualTo(userUuid)
        }
    }
}