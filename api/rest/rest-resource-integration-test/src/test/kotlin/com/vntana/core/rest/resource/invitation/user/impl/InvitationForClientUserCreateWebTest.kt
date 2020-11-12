package com.vntana.core.rest.resource.invitation.user.impl

import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.resource.invitation.user.AbstractInvitationUserWebTest
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Diana Gevorgyan
 * Date: 11/11/20
 * Time: 11:55 AM
 */
class InvitationForClientUserCreateWebTest : AbstractInvitationUserWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.createInvitationForClient(resourceTestHelper.buildCreateInvitationUserForClientRequest(userRoles = null)),
                InvitationUserErrorResponseModel.MISSING_USER_ROLES
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.createInvitationForClient(resourceTestHelper.buildCreateInvitationUserForClientRequest(email = null)),
                InvitationUserErrorResponseModel.MISSING_INVITED_USER_EMAIL
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.createInvitationForClient(resourceTestHelper.buildCreateInvitationUserForClientRequest(email = emptyString())),
                InvitationUserErrorResponseModel.MISSING_INVITED_USER_EMAIL
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.createInvitationForClient(resourceTestHelper.buildCreateInvitationUserForClientRequest(inviterUserUuid = null)),
                InvitationUserErrorResponseModel.MISSING_INVITER_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.createInvitationForClient(resourceTestHelper.buildCreateInvitationUserForClientRequest(inviterUserUuid = emptyString())),
                InvitationUserErrorResponseModel.MISSING_INVITER_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.createInvitationForClient(resourceTestHelper.buildCreateInvitationUserForClientRequest(organizationUuid = null)),
                InvitationUserErrorResponseModel.MISSING_INVITING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.createInvitationForClient(resourceTestHelper.buildCreateInvitationUserForClientRequest(organizationUuid = emptyString())),
                InvitationUserErrorResponseModel.MISSING_INVITING_ORGANIZATION_UUID
        )
    }

    @Test
    fun `test when inviter user does not exist`() {
        assertBasicErrorResultResponse(
                HttpStatus.NOT_FOUND,
                invitationUserResourceClient.createInvitationForClient(resourceTestHelper.buildCreateInvitationUserForClientRequest()),
                InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND
        )
    }

    @Test
    fun `test when organization does not exist`() {
        val inviterUserUuid = userResourceTestHelper.persistUser().response().uuid
        assertBasicErrorResultResponse(
                HttpStatus.NOT_FOUND,
                invitationUserResourceClient.createInvitationForClient(resourceTestHelper.buildCreateInvitationUserForClientRequest(inviterUserUuid = inviterUserUuid)),
                InvitationUserErrorResponseModel.INVITING_ORGANIZATION_NOT_FOUND
        )
    }

    @Test
    fun `test when client does not exist`() {
        val inviterUserUuid = userResourceTestHelper.persistUser().response().uuid
        val organization = organizationResourceTestHelper.persistOrganization()
        val organizationUuid = organization.response().uuid
        assertBasicErrorResultResponse(
                HttpStatus.CONFLICT,
                invitationUserResourceClient.createInvitationForClient(resourceTestHelper
                        .buildCreateInvitationUserForClientRequest(inviterUserUuid = inviterUserUuid, organizationUuid = organizationUuid)),
                InvitationUserErrorResponseModel.WRONG_PERMISSIONS
        )
    }
}