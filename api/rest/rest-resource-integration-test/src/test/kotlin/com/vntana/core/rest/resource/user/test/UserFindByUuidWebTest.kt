package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 12/18/19
 * Time: 11:49 AM
 */
class UserFindByUuidWebTest : AbstractUserWebTest() {

    @Test
    fun `test when user not found`() {
        val userUuid = uuid()
        userResourceClient.findByUuid(userUuid).let {
            assertBasicErrorResultResponse(it, UserErrorResponseModel.NOT_FOUND_FOR_UUID)
        }
    }

    @Test
    fun test() {
        val email = resourceHelper.email()
        val userUuid = resourceHelper.persistUser(resourceHelper.buildCreateUserRequest(email = email)).response().uuid
        userResourceClient.findByUuid(userUuid).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().email).isEqualTo(email)
            assertThat(it.response().uuid).isEqualTo(userUuid)
        }
    }
}