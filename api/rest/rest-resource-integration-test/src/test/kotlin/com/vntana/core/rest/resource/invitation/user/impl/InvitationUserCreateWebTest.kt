package com.vntana.core.rest.resource.invitation.user.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.resource.invitation.user.AbstractInvitationUserWebTest
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 6:35 PM
 */
class InvitationUserCreateWebTest : AbstractInvitationUserWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.create(resourceTestHelper.buildCreateInvitationUserRequest(userRole = null)),
                InvitationUserErrorResponseModel.MISSING_USER_ROLE
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.create(resourceTestHelper.buildCreateInvitationUserRequest(email = null)),
                InvitationUserErrorResponseModel.MISSING_USER_EMAIL
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.create(resourceTestHelper.buildCreateInvitationUserRequest(email = emptyString())),
                InvitationUserErrorResponseModel.MISSING_USER_EMAIL
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.create(resourceTestHelper.buildCreateInvitationUserRequest(inviterUserUuid = null)),
                InvitationUserErrorResponseModel.MISSING_INVITER_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.create(resourceTestHelper.buildCreateInvitationUserRequest(inviterUserUuid = emptyString())),
                InvitationUserErrorResponseModel.MISSING_INVITER_USER_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.create(resourceTestHelper.buildCreateInvitationUserRequest(organizationUuid = null)),
                InvitationUserErrorResponseModel.MISSING_INVITING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.create(resourceTestHelper.buildCreateInvitationUserRequest(organizationUuid = emptyString())),
                InvitationUserErrorResponseModel.MISSING_INVITING_ORGANIZATION_UUID
        )
    }

    @Test
    fun `test when invited role is owner`() {
        assertBasicErrorResultResponse(
                HttpStatus.CONFLICT,
                invitationUserResourceClient.create(resourceTestHelper.buildCreateInvitationUserRequest(userRole = UserRoleModel.ORGANIZATION_OWNER)),
                InvitationUserErrorResponseModel.INVITED_USER_ROLE_COULD_NOT_BE_ORGANIZATION_OWNER
        )
    }

    @Test
    fun `test when inviter user does not exist`() {
        assertBasicErrorResultResponse(
                HttpStatus.NOT_FOUND,
                invitationUserResourceClient.create(resourceTestHelper.buildCreateInvitationUserRequest()),
                InvitationUserErrorResponseModel.INVITER_USER_NOT_FOUND
        )
    }

    @Test
    fun `test when organization does not exist`() {
        val inviterUserUuid = userResourceTestHelper.persistUser().response().uuid
        assertBasicErrorResultResponse(
                HttpStatus.NOT_FOUND,
                invitationUserResourceClient.create(resourceTestHelper.buildCreateInvitationUserRequest(inviterUserUuid = inviterUserUuid)),
                InvitationUserErrorResponseModel.INVITING_ORGANIZATION_NOT_FOUND
        )
    }

    @Test
    fun `test when invited user is already part of organization`() {
        val inviterUserUuid = userResourceTestHelper.persistUser().response().uuid
        val email = email()
        val invitedUserUuid = userResourceTestHelper.persistUser(userResourceTestHelper.buildCreateUserRequest(email = email)).response().uuid
        val organizationUuid = organizationResourceTestHelper.persistOrganization(userUuid = invitedUserUuid).response().uuid
        assertBasicErrorResultResponse(
                HttpStatus.CONFLICT,
                invitationUserResourceClient.create(resourceTestHelper.buildCreateInvitationUserRequest(inviterUserUuid = inviterUserUuid, organizationUuid = organizationUuid, email = email)),
                InvitationUserErrorResponseModel.USER_ALREADY_PART_OF_ORGANIZATION
        )
    }

    @Test
    fun test() {
        val inviterUserUuid = userResourceTestHelper.persistUser().response().uuid
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        assertBasicSuccessResultResponse(invitationUserResourceClient.create(resourceTestHelper.buildCreateInvitationUserRequest(inviterUserUuid = inviterUserUuid, organizationUuid = organizationUuid)))
    }
}