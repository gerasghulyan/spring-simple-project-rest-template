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
    fun `test with invalid uuid`() {
        userResourceClient.verify(resourceHelper.buildVerifyUserRequest(uuid = "")).let {
            assertBasicErrorResultResponse(it, UserErrorResponseModel.MISSING_UUID)
        }
        userResourceClient.verify(resourceHelper.buildVerifyUserRequest(uuid = null)).let {
            assertBasicErrorResultResponse(it, UserErrorResponseModel.MISSING_UUID)
        }
    }

    @Test
    fun `test when user not found`() {
        userResourceClient.verify(resourceHelper.buildVerifyUserRequest()).let {
            assertBasicErrorResultResponse(it, UserErrorResponseModel.NOT_FOUND_FOR_UUID)
        }
    }

    @Test
    fun `test`() {
        val userUuid = resourceHelper.persistUser().response().uuid
        userResourceClient.verify(resourceHelper.buildVerifyUserRequest(uuid = userUuid)).let {
            assertBasicSuccessResultResponse(it)
        }
    }

    @Test
    fun `test when user already verified`() {
        val userUuid = resourceHelper.persistVerifiedUser()
        userResourceClient.verify(resourceHelper.buildVerifyUserRequest(uuid = userUuid)).let {
            assertBasicErrorResultResponse(it, UserErrorResponseModel.USER_ALREADY_VERIFIED)
        }
    }
}