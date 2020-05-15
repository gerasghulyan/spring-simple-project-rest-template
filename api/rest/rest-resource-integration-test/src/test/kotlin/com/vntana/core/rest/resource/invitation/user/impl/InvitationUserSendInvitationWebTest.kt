package com.vntana.core.rest.resource.invitation.user.impl

import com.sflpro.notifier.api.model.common.result.ResultResponseModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.notification.payload.invitation.user.InvitationUserEmailSendPayload
import com.vntana.core.rest.resource.invitation.user.AbstractInvitationUserWebTest
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 11:42 AM
 */
class InvitationUserSendInvitationWebTest : AbstractInvitationUserWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                invitationUserResourceClient.sendInvitation(resourceTestHelper.buildSendInvitationUserRequest(email = null)),
                InvitationUserErrorResponseModel.MISSING_INVITED_USER_EMAIL
        )
        assertBasicErrorResultResponse(
                invitationUserResourceClient.sendInvitation(resourceTestHelper.buildSendInvitationUserRequest(email = emptyString())),
                InvitationUserErrorResponseModel.MISSING_INVITED_USER_EMAIL
        )
        assertBasicErrorResultResponse(
                invitationUserResourceClient.sendInvitation(resourceTestHelper.buildSendInvitationUserRequest(token = null)),
                InvitationUserErrorResponseModel.MISSING_INVITATION_TOKEN
        )
        assertBasicErrorResultResponse(
                invitationUserResourceClient.sendInvitation(resourceTestHelper.buildSendInvitationUserRequest(token = emptyString())),
                InvitationUserErrorResponseModel.MISSING_INVITATION_TOKEN
        )
        assertBasicErrorResultResponse(
                invitationUserResourceClient.sendInvitation(resourceTestHelper.buildSendInvitationUserRequest(inviterUserUuid = null)),
                InvitationUserErrorResponseModel.MISSING_INVITER_USER_UUID
        )
        assertBasicErrorResultResponse(
                invitationUserResourceClient.sendInvitation(resourceTestHelper.buildSendInvitationUserRequest(inviterUserUuid = emptyString())),
                InvitationUserErrorResponseModel.MISSING_INVITER_USER_UUID
        )
        assertBasicErrorResultResponse(
                invitationUserResourceClient.sendInvitation(resourceTestHelper.buildSendInvitationUserRequest(organizationUuid = null)),
                InvitationUserErrorResponseModel.MISSING_INVITING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(
                invitationUserResourceClient.sendInvitation(resourceTestHelper.buildSendInvitationUserRequest(organizationUuid = emptyString())),
                InvitationUserErrorResponseModel.MISSING_INVITING_ORGANIZATION_UUID
        )
    }

    @Test
    fun `test when inviter user does not exist`() {
        assertBasicErrorResultResponse(
                HttpStatus.NOT_FOUND,
                invitationUserResourceClient.sendInvitation(resourceTestHelper.buildSendInvitationUserRequest()),
                InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND
        )
    }

    @Test
    fun `test when inviting organization does not exist`() {
        val inviterUserUuid = userResourceTestHelper.persistUser().response().uuid
        assertBasicErrorResultResponse(
                HttpStatus.NOT_FOUND,
                invitationUserResourceClient.sendInvitation(resourceTestHelper.buildSendInvitationUserRequest(inviterUserUuid = inviterUserUuid)),
                InvitationUserErrorResponseModel.INVITING_ORGANIZATION_NOT_FOUND
        )
    }

    @Test
    fun test() {
        reset(emailNotificationResourceClient)
        `when`(emailNotificationResourceClient.createEmailNotification(ArgumentMatchers.any())).thenReturn(ResultResponseModel())
        val email = email()
        val inviterUserFullName = uuid()
        val organizationName = uuid()
        val organizationUuid = organizationResourceTestHelper.persistOrganization(name = organizationName).response().uuid
        val inviterUserUuid = userResourceTestHelper.persistUser(createUserRequest = userResourceTestHelper.buildCreateUserRequest(fullName = inviterUserFullName)).response().uuid
        val request = resourceTestHelper.buildSendInvitationUserRequest(email = email, inviterUserUuid = inviterUserUuid, organizationUuid = organizationUuid)
        assertBasicSuccessResultResponse(invitationUserResourceClient.sendInvitation(request))
        verify(emailNotificationResourceClient, times(1))
                .createEmailNotification(ArgumentMatchers.argThat { argument ->
                    argument.recipientEmail == request.email &&
                            argument.properties[InvitationUserEmailSendPayload.PROPERTIES_INVITER_USER_FULL_NAME] == inviterUserFullName &&
                            argument.properties[InvitationUserEmailSendPayload.PROPERTIES_ORGANIZATION_NAME] == organizationName &&
                            argument.properties[InvitationUserEmailSendPayload.PROPERTIES_LINK]!!.contains(request.token)
                })

    }
}