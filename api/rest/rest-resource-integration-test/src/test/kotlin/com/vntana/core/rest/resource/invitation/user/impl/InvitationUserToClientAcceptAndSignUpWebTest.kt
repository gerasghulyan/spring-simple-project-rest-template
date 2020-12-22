package com.vntana.core.rest.resource.invitation.user.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.InvitationStatusModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.model.invitation.user.request.SingleUserInvitationToClientRequestModel
import com.vntana.core.rest.resource.invitation.user.AbstractInvitationUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Diana Gevorgyan.
 * Date: 11/25/20
 * Time: 8:34 PM
 */
class InvitationUserToClientAcceptAndSignUpWebTest : AbstractInvitationUserWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.acceptAndSignUpForClient(resourceTestHelper.buildAcceptInvitationUserAndSignUpRequest(token = null)),
                InvitationUserErrorResponseModel.MISSING_INVITATION_TOKEN
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.acceptAndSignUpForClient(resourceTestHelper.buildAcceptInvitationUserAndSignUpRequest(token = emptyString())),
                InvitationUserErrorResponseModel.MISSING_INVITATION_TOKEN
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.acceptAndSignUpForClient(resourceTestHelper.buildAcceptInvitationUserAndSignUpRequest(newUserFullName = null)),
                InvitationUserErrorResponseModel.MISSING_USER_FULL_NAME
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.acceptAndSignUpForClient(resourceTestHelper.buildAcceptInvitationUserAndSignUpRequest(newUserFullName = emptyString())),
                InvitationUserErrorResponseModel.MISSING_USER_FULL_NAME
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.acceptAndSignUpForClient(resourceTestHelper.buildAcceptInvitationUserAndSignUpRequest(password = null)),
                InvitationUserErrorResponseModel.MISSING_PASSWORD
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.acceptAndSignUpForClient(resourceTestHelper.buildAcceptInvitationUserAndSignUpRequest(password = emptyString())),
                InvitationUserErrorResponseModel.MISSING_PASSWORD
        )
    }

    @Test
    fun `test when token not found`() {
        assertBasicErrorResultResponse(HttpStatus.NOT_FOUND,
                invitationUserResourceClient.acceptAndSignUpForClient(resourceTestHelper.buildAcceptInvitationUserAndSignUpRequest()),
                InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN
        )
    }

    @Test
    fun `test when token is expired`() {
        val token = uuid()
        val invitationEmail = email()
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val clientUuid = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid).response().uuid

        val inviterUserUuid = userResourceTestHelper.persistUser(userResourceTestHelper.buildCreateUserRequest()).response().uuid
        userRoleResourceTestHelper.grantUserClientRole(inviterUserUuid, clientUuid = clientUuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_ADMIN)
        val userInvitationUuid = resourceTestHelper
                .persistInvitationUserToClient(listOf(SingleUserInvitationToClientRequestModel(clientUuid, UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)), invitationEmail, inviterUserUuid, organizationUuid)!![0]
        tokenResourceTestHelper.persistTokenInvitationUserToClient(token = token, invitationUserUuid = userInvitationUuid)
        tokenResourceTestHelper.expire(token)
        assertBasicErrorResultResponse(HttpStatus.NOT_ACCEPTABLE,
                invitationUserResourceClient.acceptAndSignUpForClient(resourceTestHelper.buildAcceptInvitationUserAndSignUpRequest(token = token)),
                InvitationUserErrorResponseModel.TOKEN_IS_EXPIRED
        )
    }

    @Test
    fun test() {
        val token = uuid()
        val invitationEmail = email()
        val newUserFullName = "Name"
        val password = uuid()

        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val clientUuid = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid).response().uuid

        val inviterUserUuid = userResourceTestHelper.persistUser(userResourceTestHelper.buildCreateUserRequest()).response().uuid
        userRoleResourceTestHelper.grantUserClientRole(inviterUserUuid, clientUuid = clientUuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_ADMIN)
        val userInvitationUuid = resourceTestHelper
                .persistInvitationUserToClient(listOf(SingleUserInvitationToClientRequestModel(clientUuid, UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)), invitationEmail, inviterUserUuid, organizationUuid)!![0]
        tokenResourceTestHelper.persistTokenInvitationUserToClient(token = token, invitationUserUuid = userInvitationUuid)
        val request = resourceTestHelper.buildAcceptInvitationUserAndSignUpRequest(token = token, newUserFullName = newUserFullName, password = password)
        invitationUserResourceClient.acceptAndSignUpForClient(request).let { responseEntity ->
            assertBasicSuccessResultResponse(responseEntity)
            responseEntity?.body?.response()?.let {
                assertThat(it.clientUuid).isEqualTo(clientUuid)
                assertThat(it.organizationUuid).isEqualTo(organizationUuid)
                assertThat(it.userRoleModel).isEqualTo(UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)
                assertThat(it.userUuid).isNotEmpty()
                assertThat(userResourceClient.accountDetails(it.userUuid)?.body?.response()?.isEmailVerified).isTrue()
            }
        }
        assertThat(userResourceClient.getUsersByClientOrganization(clientUuid)?.body?.response()?.items()?.map { model -> model.email }).contains(invitationEmail)
        assertThat(tokenResourceClient.isExpire(request.token)?.body?.response()?.expired).isTrue()
    }
}