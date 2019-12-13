package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 12/13/19
 * Time: 10:26 AM
 */
class UserResetPasswordWebTest : AbstractUserWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                userResourceClient.resetPassword(resourceHelper.buildResetUserPasswordRequest(email = null)),
                UserErrorResponseModel.MISSING_EMAIL
        )
        assertBasicErrorResultResponse(
                userResourceClient.resetPassword(resourceHelper.buildResetUserPasswordRequest(email = "")),
                UserErrorResponseModel.MISSING_EMAIL
        )
        assertBasicErrorResultResponse(
                userResourceClient.resetPassword(resourceHelper.buildResetUserPasswordRequest(password = null)),
                UserErrorResponseModel.MISSING_PASSWORD
        )
        assertBasicErrorResultResponse(
                userResourceClient.resetPassword(resourceHelper.buildResetUserPasswordRequest(password = "")),
                UserErrorResponseModel.MISSING_PASSWORD
        )
    }

    @Test
    fun `test when user not found`() {
        val request = resourceHelper.buildResetUserPasswordRequest()
        assertBasicErrorResultResponse(userResourceClient.resetPassword(request), UserErrorResponseModel.NOT_FOUND_FOR_EMAIL)
    }

    @Test
    fun test() {
        val email = resourceHelper.email()
        val newPassword = uuid()
        val createRequest = resourceHelper.buildCreateUserRequest(email = email)
        resourceHelper.persistUser(createRequest)
        val request = resourceHelper.buildResetUserPasswordRequest(email = email, password = newPassword)
        userResourceClient.resetPassword(request).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().email).isEqualTo(email)
        }
    }
}