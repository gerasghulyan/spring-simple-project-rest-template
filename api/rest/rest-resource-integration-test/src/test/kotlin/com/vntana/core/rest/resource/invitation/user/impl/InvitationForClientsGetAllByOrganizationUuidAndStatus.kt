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
 * Time: 7:20 PM
 */
class InvitationForClientsGetAllByOrganizationUuidAndStatus : AbstractInvitationUserWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.getAllInvitationsToClientsByStatus(resourceTestHelper.buildGetAllByStatusInvitationUserRequest(organizationUuid = null)),
                InvitationUserErrorResponseModel.MISSING_INVITING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.getAllInvitationsToClientsByStatus(resourceTestHelper.buildGetAllByStatusInvitationUserRequest(organizationUuid = emptyString())),
                InvitationUserErrorResponseModel.MISSING_INVITING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.getAllInvitationsToClientsByStatus(resourceTestHelper.buildGetAllByStatusInvitationUserRequest(invitationStatus = null)),
                InvitationUserErrorResponseModel.MISSING_INVITATION_STATUS
        )
    }

    @Test
    fun `test when inviting organization not found`() {
        assertBasicErrorResultResponse(
                HttpStatus.NOT_FOUND,
                invitationUserResourceClient.getAllInvitationsToClientsByStatus(resourceTestHelper.buildGetAllByStatusInvitationUserRequest(organizationUuid = uuid())),
                InvitationUserErrorResponseModel.INVITING_ORGANIZATION_NOT_FOUND
        )
    }

    @Test
    fun test() {
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val client1 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid).response().uuid
        val client2 = clientOrganizationResourceTestHelper.persistClientOrganization(organizationUuid = organizationUuid).response().uuid

        val inviterUuid = userResourceTestHelper.persistUser().response().uuid
        userRoleResourceTestHelper.grantUserOrganizationAdminRole(inviterUuid, organizationUuid)


        val expectedInvitationUuid1 = resourceTestHelper.persistInvitationUserToClient(organizationUuid = organizationUuid, inviterUserUuid = inviterUuid, userRoleRequests = listOf(SingleUserInvitationToClientRequestModel(client1, UserRoleModel.CLIENT_ORGANIZATION_VIEWER)))?.get(0)
        val expectedInvitationUuid2 = resourceTestHelper.persistInvitationUserToClient(organizationUuid = organizationUuid, inviterUserUuid = inviterUuid, userRoleRequests = listOf(SingleUserInvitationToClientRequestModel(client2, UserRoleModel.CLIENT_ORGANIZATION_VIEWER)))?.get(0)

        val request = resourceTestHelper.buildGetAllByStatusInvitationUserRequest(
                invitationStatus = InvitationStatusModel.INVITED,
                organizationUuid = organizationUuid
        )
        val responseEntity = invitationUserResourceClient.getAllInvitationsToClientsByStatus(request)
        assertBasicSuccessResultResponse(responseEntity)
        responseEntity?.body?.response()?.let {
            val uuids = it.items().map { model -> model.uuid }.toList()
            assertThat(uuids).contains(expectedInvitationUuid1, expectedInvitationUuid2)
        }
    }
}