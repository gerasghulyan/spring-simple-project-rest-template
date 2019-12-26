package com.vntana.core.rest.resource.invitation.impl

import com.vntana.core.model.invitation.error.InvitationErrorResponseModel
import com.vntana.core.rest.resource.invitation.AbstractInvitationWebTest
import org.junit.Test
import org.mockito.ArgumentMatchers.argThat
import org.mockito.Mockito
import org.mockito.Mockito.times

/**
 * Created by Arman Gevorgyan.
 * Date: 12/26/19
 * Time: 10:50 AM
 */
class InvitationInviteToPlatformWebTest : AbstractInvitationWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(invitationResourceClient.inviteToPlatform(
                resourceTestHelper.buildInvitationToPlatformRequest(token = null)),
                InvitationErrorResponseModel.MISSING_TOKEN
        )
        assertBasicErrorResultResponse(invitationResourceClient.inviteToPlatform(
                resourceTestHelper.buildInvitationToPlatformRequest(token = "")),
                InvitationErrorResponseModel.MISSING_TOKEN
        )
        assertBasicErrorResultResponse(invitationResourceClient.inviteToPlatform(
                resourceTestHelper.buildInvitationToPlatformRequest(email = null)),
                InvitationErrorResponseModel.MISSING_EMAIL
        )
        assertBasicErrorResultResponse(invitationResourceClient.inviteToPlatform(
                resourceTestHelper.buildInvitationToPlatformRequest(email = "")),
                InvitationErrorResponseModel.MISSING_EMAIL
        )
    }

    @Test
    fun `test when user with email does not exist`() {
        val request = resourceTestHelper.buildInvitationToPlatformRequest()
        assertBasicSuccessResultResponse(invitationResourceClient.inviteToPlatform(request))
        Mockito.verify(emailNotificationResourceClient, times(1))
                .createEmailNotification(argThat { argument -> argument.recipientEmail == request.email })
    }

    @Test
    fun test() {
        val userEmail = userResourceTestHelper.email()
        userResourceTestHelper.persistUser(userResourceTestHelper.buildCreateUserRequest(email = userEmail))
        val request = resourceTestHelper.buildInvitationToPlatformRequest(email = userEmail)
        assertBasicErrorResultResponse(invitationResourceClient.inviteToPlatform(request), InvitationErrorResponseModel.USER_ALREADY_EXISTS)
    }
}