package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.junit.Test

/**
 * Created by Geras Ghulyan.
 * Date: 12/13/19
 * Time: 10:26 AM
 */
class UserCheckResetPasswordWebTest : AbstractUserWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                userResourceClient.checkResetPasswordToken(" "),
                UserErrorResponseModel.INVALID_RESET_PASSWORD_TOKEN
        )
    }

    @Test
    fun `test when user not found`() {
        assertBasicErrorResultResponse(userResourceClient.checkResetPasswordToken(uuid()), UserErrorResponseModel.INVALID_RESET_PASSWORD_TOKEN)
    }

    @Test
    fun test() {
        val token = uuid()
        val createRequest = resourceHelper.buildCreateUserRequest()
        resourceHelper.persistUser(createRequest)
        resourceHelper.persistTokenResetPassword(createRequest.email, token)
        userResourceClient.checkResetPasswordToken(token).let {
            assertBasicSuccessResultResponse(it)
        }
    }
}