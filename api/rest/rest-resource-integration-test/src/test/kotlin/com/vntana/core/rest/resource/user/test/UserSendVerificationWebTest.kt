package com.vntana.core.rest.resource.user.test

import com.sflpro.notifier.api.model.common.result.ResultResponseModel
import com.sflpro.notifier.api.model.email.response.CreateEmailNotificationResponse
import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

/**
 * Created by Arman Gevorgyan.
 * Date: 11/12/19
 * Time: 1:53 PM
 */
class UserSendVerificationWebTest : AbstractUserWebTest() {

    @Test
    fun `test with invalid request arguments`() {
        userResourceClient.sendVerification(resourceHelper.buildSendUserVerificationRequest(email = null)).let {
            assertBasicErrorResultResponse(it, UserErrorResponseModel.MISSING_EMAIL)
        }
        userResourceClient.sendVerification(resourceHelper.buildSendUserVerificationRequest(token = null)).let {
            assertBasicErrorResultResponse(it, UserErrorResponseModel.MISSING_VERIFICATION_TOKEN)
        }
    }

    @Test
    fun `test when user does not exist`() {
        assertBasicErrorResultResponse(userResourceClient.sendVerification(resourceHelper.buildSendUserVerificationRequest()), UserErrorResponseModel.NOT_FOUND_FOR_UUID)
    }

    @Test
    fun `test when user already verified`() {
        val email = email()
        resourceHelper.persistVerifiedUser(resourceHelper.buildCreateUserRequest(email = email))
        userResourceClient.sendVerification(resourceHelper.buildSendUserVerificationRequest(email = email)).let {
            assertBasicErrorResultResponse(it, UserErrorResponseModel.USER_ALREADY_VERIFIED)
        }
    }

    @Test
    fun `test sendVerification`() {
        Mockito.reset(emailNotificationResourceClient)
        Mockito.`when`(emailNotificationResourceClient.createEmailNotification(ArgumentMatchers.any())).thenReturn(ResultResponseModel<CreateEmailNotificationResponse>())
        val email = email()
        val userUuid = resourceHelper.persistUser(resourceHelper.buildCreateUserRequest(email = email)).response().uuid
        userResourceClient.sendVerification(resourceHelper.buildSendUserVerificationRequest(email = email)).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().uuid).isEqualTo(userUuid)
        }
    }
}