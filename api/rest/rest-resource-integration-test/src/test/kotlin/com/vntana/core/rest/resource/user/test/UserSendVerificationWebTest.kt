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
        userResourceClient.sendVerification(resourceHelper.buildSendUserVerificationRequest(uuid = null)).let {
            assertBasicErrorResultResponse(it, UserErrorResponseModel.MISSING_UUID)
        }
        userResourceClient.sendVerification(resourceHelper.buildSendUserVerificationRequest(token = null)).let {
            assertBasicErrorResultResponse(it, UserErrorResponseModel.MISSING_VERIFICATION_TOKEN)
        }
    }

    @Test
    fun `test when user does not exist`() {
        userResourceClient.sendVerification(resourceHelper.buildSendUserVerificationRequest()).let {
            assertBasicErrorResultResponse(it, UserErrorResponseModel.NOT_FOUND_FOR_UUID)
        }
    }

    @Test
    fun `test when user already verified`() {
        val userUuid = resourceHelper.persistVerifiedUser()
        userResourceClient.sendVerification(resourceHelper.buildSendUserVerificationRequest(uuid = userUuid)).let {
            assertBasicErrorResultResponse(it, UserErrorResponseModel.USER_ALREADY_VERIFIED)
        }
    }

    @Test
    fun `test`() {
        Mockito.reset(emailNotificationResourceClient)
        Mockito.`when`(emailNotificationResourceClient.createEmailNotification(ArgumentMatchers.any())).thenReturn(ResultResponseModel<CreateEmailNotificationResponse>())
        var userUuid = resourceHelper.persistUser().response().uuid
        userResourceClient.sendVerification(resourceHelper.buildSendUserVerificationRequest(uuid = userUuid)).let {
            assertBasicSuccessResultResponse(it)
            assertThat(it.response().uuid).isEqualTo(userUuid)
        }
    }
}