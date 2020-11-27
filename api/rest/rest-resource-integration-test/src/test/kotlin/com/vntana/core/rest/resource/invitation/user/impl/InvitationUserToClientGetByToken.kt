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
 * Created by Diana Gevorgyan
 * Date: 11/27/20
 * Time: 10:25 AM
 */
class InvitationUserToClientGetByToken : AbstractInvitationUserWebTest() {

    @Test
    fun `test token not found`() {
        assertBasicErrorResultResponse(
                HttpStatus.NOT_FOUND,
                invitationUserResourceClient.getByTokenInvitationToClient(uuid()),
                InvitationUserErrorResponseModel.NOT_FOUND_FOR_TOKEN
        )
    }
    
    @Test
    fun `test when invited user does not exist`() {
        val token = uuid()
        val email = email()

        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val clientUuid = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid).response().uuid

        val inviterUserUuid = userResourceTestHelper.persistUser(userResourceTestHelper.buildCreateUserRequest()).response().uuid
        userRoleResourceTestHelper.grantUserClientRole(inviterUserUuid, clientUuid = clientUuid, userRole = UserRoleModel.CLIENT_ORGANIZATION_ADMIN)
        val userInvitationUuid = resourceTestHelper
                .persistInvitationUserToClient(listOf(SingleUserInvitationToClientRequestModel(clientUuid, UserRoleModel.CLIENT_ORGANIZATION_CONTENT_MANAGER)), email, inviterUserUuid, organizationUuid)!![0]
        tokenResourceTestHelper.persistTokenInvitationUserToClient(token = token, invitationUserUuid = userInvitationUuid)

        val responseEntity = invitationUserResourceClient.getByTokenInvitationToClient(token)
        assertBasicSuccessResultResponse(responseEntity)
        val response = responseEntity?.body?.response()
        assertThat(response?.uuid).isNotBlank()
        assertThat(response?.invitedUserExists).isFalse()
        assertThat(response?.invitedUserEmail).isEqualTo(email)
        assertThat(response?.status).isEqualTo(InvitationStatusModel.INVITED)
    }
}