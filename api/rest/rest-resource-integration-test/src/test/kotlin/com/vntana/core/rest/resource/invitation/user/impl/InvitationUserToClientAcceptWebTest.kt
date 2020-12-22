package com.vntana.core.rest.resource.invitation.user.impl

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.model.invitation.user.request.SingleUserInvitationToClientRequestModel
import com.vntana.core.rest.resource.invitation.user.AbstractInvitationUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Diana Gevorgyan
 * Date: 11/25/20
 * Time: 6:37 PM
 */
class InvitationUserToClientAcceptWebTest : AbstractInvitationUserWebTest() {

    @Test
    fun `test when token not found`() {
        assertBasicErrorResultResponse(
            HttpStatus.NOT_FOUND,
            invitationUserResourceClient.acceptInvitationForClient(resourceTestHelper.buildAcceptInvitationUserRequest()),
            InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN
        )
    }

    @Test
    fun `test when already has any role`() {
        val token = uuid()
        val invitationEmail = email()

        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val clientUuid =
            clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid)
                .response().uuid

        val inviterUserUuid =
            userResourceTestHelper.persistUser(userResourceTestHelper.buildCreateUserRequest()).response().uuid
        userRoleResourceTestHelper.grantUserClientRole(
            inviterUserUuid,
            clientUuid = clientUuid,
            userRole = UserRoleModel.CLIENT_ORGANIZATION_ADMIN
        )

        val invitedUserUuid =
            userResourceTestHelper.persistUser(userResourceTestHelper.buildCreateUserRequest(email = invitationEmail))
                .response().uuid
        userRoleResourceTestHelper.grantUserClientRole(
            invitedUserUuid,
            clientUuid = clientUuid,
            userRole = UserRoleModel.CLIENT_ORGANIZATION_ADMIN
        )

        val userInvitationUuid = resourceTestHelper
            .persistInvitationUserToClient(
                listOf(
                    SingleUserInvitationToClientRequestModel(
                        clientUuid,
                        UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER
                    )
                ), invitationEmail, inviterUserUuid, organizationUuid
            )!![0]
        tokenResourceTestHelper.persistTokenInvitationUserToClient(
            token = token,
            invitationUserUuid = userInvitationUuid
        )

        val request = resourceTestHelper.buildAcceptInvitationUserRequest(token = token)
        assertBasicErrorResultResponse(
            HttpStatus.CONFLICT,
            invitationUserResourceClient.acceptInvitationForClient(request),
            InvitationUserErrorResponseModel.USER_ALREADY_HAS_ROLE_IN_CLIENT
        )
    }

    @Test
    fun test() {
        val token = uuid()
        val invitationEmail = email()

        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val clientUuid =
            clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid)
                .response().uuid

        val inviterUserUuid =
            userResourceTestHelper.persistUser(userResourceTestHelper.buildCreateUserRequest()).response().uuid
        userRoleResourceTestHelper.grantUserClientRole(
            inviterUserUuid,
            clientUuid = clientUuid,
            userRole = UserRoleModel.CLIENT_ORGANIZATION_ADMIN
        )
        val invitedUserUuid =
            userResourceTestHelper.persistUser(userResourceTestHelper.buildCreateUserRequest(email = invitationEmail))
                .response().uuid
        val userInvitationUuid = resourceTestHelper
            .persistInvitationUserToClient(
                listOf(
                    SingleUserInvitationToClientRequestModel(
                        clientUuid,
                        UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER
                    )
                ), invitationEmail, inviterUserUuid, organizationUuid
            )!![0]
        tokenResourceTestHelper.persistTokenInvitationUserToClient(
            token = token,
            invitationUserUuid = userInvitationUuid
        )

        val request = resourceTestHelper.buildAcceptInvitationUserRequest(token = token)

        invitationUserResourceClient.acceptInvitationForClient(request).let { responseEntity ->
            assertBasicSuccessResultResponse(responseEntity)
            responseEntity?.body?.response()?.let {
                assertThat(it.clientUuid).isEqualTo(clientUuid)
                assertThat(it.organizationUuid).isEqualTo(organizationUuid)
                assertThat(it.userUuid).isEqualTo(invitedUserUuid)
                assertThat(it.userRoleModel).isEqualTo(UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)
            }
        }
        assertThat(userResourceClient.getUsersByClientOrganization(clientUuid)
            ?.body?.response()?.items()?.map { model -> model.email }
        ).contains(invitationEmail)
        assertThat(tokenResourceClient.isExpire(request.token)?.body?.response()?.expired).isTrue()
    }
}