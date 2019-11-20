package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 11/11/19
 * Time: 1:56 PM
 */
class UserVerifyWebTest : AbstractUserWebTest() {

    @Test
    fun `test with invalid email`() {
        assertBasicErrorResultResponse(userResourceClient.verify(resourceHelper.buildVerifyUserRequest(email = "")), UserErrorResponseModel.MISSING_EMAIL)
        assertBasicErrorResultResponse(userResourceClient.verify(resourceHelper.buildVerifyUserRequest(email = null)), UserErrorResponseModel.MISSING_EMAIL)
    }

    @Test
    fun `test when user not found`() {
        assertBasicErrorResultResponse(userResourceClient.verify(resourceHelper.buildVerifyUserRequest()), UserErrorResponseModel.NOT_FOUND_FOR_EMAIL)
    }

    @Test
    fun `test when user already verified`() {
        val request = resourceHelper.buildCreateUserRequest()
        resourceHelper.persistVerifiedUser(request)
        assertBasicErrorResultResponse(userResourceClient.verify(resourceHelper.buildVerifyUserRequest(email = request.email)), UserErrorResponseModel.USER_ALREADY_VERIFIED)
    }

    @Test
    fun `test verify`() {
        val request = resourceHelper.buildCreateUserRequest()
        resourceHelper.persistUser(request)
        assertBasicSuccessResultResponse(userResourceClient.verify(resourceHelper.buildVerifyUserRequest(email = request.email)))
    }
}