package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils

/**
 * Created by Manuk Gharslyan.
 * Date: 1/24/2020
 * Time: 11:15 AM
 */
class UserChangePasswordWebTest : AbstractUserWebTest() {

    @Test
    fun `test update with invalid arguments`() {
        resourceHelper.assertBasicErrorResultResponse(userResourceClient.changePassword(resourceHelper.buildChangeUserPasswordRequest(uuid = null)), UserErrorResponseModel.MISSING_UUID)
        resourceHelper.assertBasicErrorResultResponse(userResourceClient.changePassword(resourceHelper.buildChangeUserPasswordRequest(uuid = StringUtils.EMPTY)), UserErrorResponseModel.MISSING_UUID)
        resourceHelper.assertBasicErrorResultResponse(userResourceClient.changePassword(resourceHelper.buildChangeUserPasswordRequest(oldPassword = null)), UserErrorResponseModel.MISSING_PASSWORD)
        resourceHelper.assertBasicErrorResultResponse(userResourceClient.changePassword(resourceHelper.buildChangeUserPasswordRequest(oldPassword = StringUtils.EMPTY)), UserErrorResponseModel.MISSING_PASSWORD)
        resourceHelper.assertBasicErrorResultResponse(userResourceClient.changePassword(resourceHelper.buildChangeUserPasswordRequest(newPassword = null)), UserErrorResponseModel.MISSING_PASSWORD)
        resourceHelper.assertBasicErrorResultResponse(userResourceClient.changePassword(resourceHelper.buildChangeUserPasswordRequest(newPassword = StringUtils.EMPTY)), UserErrorResponseModel.MISSING_PASSWORD)
    }

    @Test
    fun `test change password`() {
        val oldPassword = uuid()
        val user = resourceHelper.persistUser(createUserRequest = resourceHelper.buildCreateUserRequest(
                password = oldPassword
        ))
        val request = resourceHelper.buildChangeUserPasswordRequest(
                uuid = user.response().uuid,
                oldPassword = oldPassword
        )
        val result = userResourceClient.changePassword(request)
        assertBasicSuccessResultResponse(result)
        assertThat(result.response().uuid).isEqualTo(request.uuid)
    }
}