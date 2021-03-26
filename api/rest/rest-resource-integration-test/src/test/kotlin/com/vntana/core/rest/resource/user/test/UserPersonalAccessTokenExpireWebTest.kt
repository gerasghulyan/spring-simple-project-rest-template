package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.user.request.CreatePersonalAccessTokenRequest
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 4:14 PM
 */
class UserPersonalAccessTokenExpireWebTest : AbstractUserWebTest() {

    @Test
    fun `test when token does not exist`() {
        val userUuid = resourceHelper.persistUser(
            createUserRequest = resourceHelper.buildCreateUserRequest()
        ).response().uuid
        userResourceClient.expirePersonalAccessTokenByUserUuid(userUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body!!.response().token).isNull()
            assertThat(it.body!!.response().userUuid).isEqualTo(userUuid)
        }
    }

    @Test
    fun `test when token exists`() {
        val userUuid = resourceHelper.persistUser(
            createUserRequest = resourceHelper.buildCreateUserRequest()
        ).response().uuid
        val token = uuid()
        resourceHelper.persistPersonalAccessToken(CreatePersonalAccessTokenRequest(token, userUuid))
        resourceHelper.userResourceClient.expirePersonalAccessTokenByUserUuid(userUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.body!!.response().token).isNull()
            assertThat(it.body!!.response().userUuid).isEqualTo(userUuid)
        }
    }
}