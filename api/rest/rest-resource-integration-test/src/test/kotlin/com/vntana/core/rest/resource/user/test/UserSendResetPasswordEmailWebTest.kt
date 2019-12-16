package com.vntana.core.rest.resource.user.test

import com.vntana.core.model.user.error.UserErrorResponseModel
import com.vntana.core.rest.resource.user.AbstractUserWebTest
import org.junit.Test
import org.mockito.ArgumentMatchers.argThat
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

/**
 * Created by Arman Gevorgyan.
 * Date: 12/12/19
 * Time: 3:42 PM
 */
class UserSendResetPasswordEmailWebTest : AbstractUserWebTest() {

    @Test
    fun `test when email not found`() {
        val request = resourceHelper.buildSendUserResetPasswordRequest()
        userResourceClient.sendResetPassword(request).let {
            assertBasicErrorResultResponse(it, UserErrorResponseModel.NOT_FOUND_FOR_EMAIL)
        }
    }

    @Test
    fun test() {
        val email = resourceHelper.email()
        resourceHelper.persistUser(resourceHelper.buildCreateUserRequest(email = email))
        val request = resourceHelper.buildSendUserResetPasswordRequest(email = email)
        userResourceClient.sendResetPassword(request).let {
            assertBasicSuccessResultResponse(it)
        }
        verify(emailNotificationResourceClient, times(2)).createEmailNotification(argThat { argument -> argument.recipientEmail == email })
    }

}