package com.vntana.core.rest.resource.auth.test

import com.vntana.core.model.security.request.CreatePersonalAccessTokenRequest
import com.vntana.core.model.security.request.FindUserByPersonalAccessTokenRequest
import com.vntana.core.rest.resource.auth.AbstractAuthWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 4:43 PM
 */
class AuthFindByPersonalAccessTokenWebTest : AbstractAuthWebTest() {

    @Test
    fun test() {
        val user = userResourceTestHelper.persistUser(
            createUserRequest = userResourceTestHelper.buildCreateUserRequest()
        ).response()
        val token = uuid()
        authResourceClient.createPersonalAccessToken(
            CreatePersonalAccessTokenRequest(
                token,
                user.uuid
            )
        )
        authResourceClient.findByPersonalAccessToken(FindUserByPersonalAccessTokenRequest(token)).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().uuid).isEqualTo(user.uuid)
        }
    }
}