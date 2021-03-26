package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.token.auth.request.TokenAuthenticationRequest
import com.vntana.core.model.user.request.CreatePersonalAccessTokenRequest
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 4:43 PM
 */
class UserPersonalAccessTokenFindByTokenWebTest : AbstractUserWebTest() {
    
    @Test
    fun test() {
        val userUuid = resourceHelper.persistUser(
            createUserRequest = resourceHelper.buildCreateUserRequest()
        ).response().uuid
        val token = uuid()
        resourceHelper.persistPersonalAccessToken(CreatePersonalAccessTokenRequest(token, userUuid))
        userResourceClient.findByToken(TokenAuthenticationRequest(token)).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body.response().userUuid).isEqualTo(userUuid)
        }
    }
}