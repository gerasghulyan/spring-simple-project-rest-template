package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.resource.user.AbstractUserWebTest
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
                userResourceClient.resetPassword(resourceHelper.buildResetUserPasswordRequest(token = null)),
                UserErrorResponseModel.MISSING_TOKEN
        )
        assertBasicErrorResultResponse(
                userResourceClient.resetPassword(resourceHelper.buildResetUserPasswordRequest(token = "")),
                UserErrorResponseModel.MISSING_TOKEN
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
        assertBasicErrorResultResponse(userResourceClient.resetPassword(request), UserErrorResponseModel.INVALID_RESET_PASSWORD_TOKEN)
    }

    @Test
    fun test() {
        val token = uuid()
        val newPassword = uuid()
        val createRequest = resourceHelper.buildCreateUserRequest()
        resourceHelper.persistUser(createRequest)
        resourceHelper.persistTokenResetPassword(createRequest.email, token)
        val request = resourceHelper.buildResetUserPasswordRequest(token = token, password = newPassword)
        //first time the request needs to success
        userResourceClient.resetPassword(request).let {
            assertBasicSuccessResultResponse(it)
        }
        //second time the request needs to fail
        userResourceClient.resetPassword(request).let {
            assertBasicErrorResultResponse(it, UserErrorResponseModel.INVALID_RESET_PASSWORD_TOKEN)
        }
    }
}