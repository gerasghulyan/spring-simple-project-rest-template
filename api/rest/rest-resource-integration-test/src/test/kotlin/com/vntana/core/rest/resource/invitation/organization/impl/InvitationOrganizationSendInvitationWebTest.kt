package com.vntana.core.rest.resource.invitation.organization.impl

import com.sflpro.notifier.api.model.common.result.ResultResponseModel
import com.sflpro.notifier.api.model.email.response.CreateEmailNotificationResponse
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.rest.resource.invitation.organization.AbstractInvitationOrganizationWebTest
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 5:55 PM
 */
class InvitationOrganizationSendInvitationWebTest : AbstractInvitationOrganizationWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                invitationOrganizationResourceClient.sendInvitation(resourceTestHelper.buildSendInvitationOrganizationRequest(email = null)),
                InvitationOrganizationErrorResponseModel.MISSING_EMAIL
        )
        assertBasicErrorResultResponse(
                invitationOrganizationResourceClient.sendInvitation(resourceTestHelper.buildSendInvitationOrganizationRequest(email = emptyString())),
                InvitationOrganizationErrorResponseModel.MISSING_EMAIL
        )
        assertBasicErrorResultResponse(
                invitationOrganizationResourceClient.sendInvitation(resourceTestHelper.buildSendInvitationOrganizationRequest(token = null)),
                InvitationOrganizationErrorResponseModel.MISSING_TOKEN
        )
        assertBasicErrorResultResponse(
                invitationOrganizationResourceClient.sendInvitation(resourceTestHelper.buildSendInvitationOrganizationRequest(token = emptyString())),
                InvitationOrganizationErrorResponseModel.MISSING_TOKEN
        )
    }

    @Test
    fun test() {
        reset(emailNotificationResourceClient)
        `when`(emailNotificationResourceClient.createEmailNotification(ArgumentMatchers.any())).thenReturn(ResultResponseModel<CreateEmailNotificationResponse>())
        val email = email()
        val request = resourceTestHelper.buildSendInvitationOrganizationRequest(email = email)
        assertBasicSuccessResultResponse(invitationOrganizationResourceClient.sendInvitation(request))
        verify(emailNotificationResourceClient, times(1))
                .createEmailNotification(ArgumentMatchers.argThat { argument -> argument.recipientEmail == request.email })

    }
}