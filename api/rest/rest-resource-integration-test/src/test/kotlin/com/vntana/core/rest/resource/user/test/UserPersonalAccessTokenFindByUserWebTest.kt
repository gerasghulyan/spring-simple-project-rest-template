package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.user.request.CreatePersonalAccessTokenRequest
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 4:54 PM
 */
class UserPersonalAccessTokenFindByUserWebTest : AbstractUserWebTest() {

    @Test
    fun test() {
        val userUuid = resourceHelper.persistUser(
            createUserRequest = resourceHelper.buildCreateUserRequest()
        ).response().uuid
        val token = uuid()
        resourceHelper.persistPersonalAccessToken(CreatePersonalAccessTokenRequest(token, userUuid))
        userResourceClient.findPersonalAccessTokenByUserUuid(userUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body!!.response().userUuid).isEqualTo(userUuid)
            assertThat(it.body!!.response().token).isEqualTo(token)
        }
    }
}