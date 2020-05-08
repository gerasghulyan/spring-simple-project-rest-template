package com.vntana.core.rest.resource.auth.test

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.model.user.request.FindUserByEmailRequest
import com.vntana.core.rest.resource.auth.AbstractAuthWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/17/20
 * Time: 6:42 PM
 */
class AuthFindByEmailWebTest : AbstractAuthWebTest() {

    @Test
    fun `test when not found`() {
        assertBasicErrorResultResponse(authResourceClient.findByEmail(FindUserByEmailRequest(uuid())), UserErrorResponseModel.NOT_FOUND_FOR_EMAIL)
    }

    @Test
    fun `test when organization owner was found`() {
        val email = userResourceTestHelper.email()
        userResourceTestHelper.persistUser(userResourceTestHelper.buildCreateUserRequest(email = email))
        authResourceClient.findByEmail(FindUserByEmailRequest(email)).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().username).isEqualTo(email)
            assertThat(it.response().roles).containsOnly(UserRoleModel.ORGANIZATION_OWNER)
        }
    }
}