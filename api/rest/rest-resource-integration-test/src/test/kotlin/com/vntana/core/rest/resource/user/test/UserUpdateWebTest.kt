package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.ArgumentMatchers.argThat
import org.mockito.Mockito.verify
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils

/**
 * Created by Manuk Gharslyan.
 * Date: 1/23/2020
 * Time: 5:32 PM
 */
class UserUpdateWebTest : AbstractUserWebTest() {

    @Test
    fun `test update with invalid arguments`() {
        assertBasicErrorResultResponse(userResourceClient.update(resourceHelper.buildUpdateUserRequest(uuid = null)), UserErrorResponseModel.MISSING_UUID)
        assertBasicErrorResultResponse(userResourceClient.update(resourceHelper.buildUpdateUserRequest(uuid = StringUtils.EMPTY)), UserErrorResponseModel.MISSING_UUID)
        assertBasicErrorResultResponse(userResourceClient.update(resourceHelper.buildUpdateUserRequest(fullName = null)), UserErrorResponseModel.MISSING_FULL_NAME)
        assertBasicErrorResultResponse(userResourceClient.update(resourceHelper.buildUpdateUserRequest(fullName = StringUtils.EMPTY)), UserErrorResponseModel.MISSING_FULL_NAME)
    }

    @Test
    fun `test update`() {
        val userFullName = uuid()
        val organizationName = uuid()
        val user = resourceHelper.persistUser(createUserRequest = resourceHelper.buildCreateUserRequest(
                clientName = organizationName
        ))
        val request = resourceHelper.buildUpdateUserRequest(uuid = user.response().uuid,fullName = userFullName)
        val result = userResourceClient.update(request)
        assertBasicSuccessResultResponse(result)
        assertThat(result.response().uuid).isEqualTo(request.uuid)
        verify(customerResourceClient).update(argThat { inRequest -> inRequest.organizationName == organizationName &&
                inRequest.userFullName == userFullName})
    }
}