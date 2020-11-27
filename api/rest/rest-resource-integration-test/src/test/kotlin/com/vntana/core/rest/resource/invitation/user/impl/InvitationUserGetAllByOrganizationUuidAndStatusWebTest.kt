package com.vntana.core.rest.resource.invitation.user.impl

import com.vntana.core.model.invitation.InvitationStatusModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.resource.invitation.user.AbstractInvitationUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Manuk Gharslyan.
 * Date: 5/13/2020
 * Time: 4:14 PM
 */
class InvitationUserGetAllByOrganizationUuidAndStatusWebTest : AbstractInvitationUserWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.getAllInvitationsToOrganizationByStatus(resourceTestHelper.buildGetAllByStatusInvitationUserRequest(organizationUuid = null)),
                InvitationUserErrorResponseModel.MISSING_INVITING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.getAllInvitationsToOrganizationByStatus(resourceTestHelper.buildGetAllByStatusInvitationUserRequest(organizationUuid = emptyString())),
                InvitationUserErrorResponseModel.MISSING_INVITING_ORGANIZATION_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.getAllInvitationsToOrganizationByStatus(resourceTestHelper.buildGetAllByStatusInvitationUserRequest(invitationStatus = null)),
                InvitationUserErrorResponseModel.MISSING_INVITATION_STATUS
        )
    }

    @Test
    fun `test when inviting organization not found`() {
        assertBasicErrorResultResponse(
                HttpStatus.NOT_FOUND,
                invitationUserResourceClient.getAllInvitationsToOrganizationByStatus(resourceTestHelper.buildGetAllByStatusInvitationUserRequest(organizationUuid = uuid())),
                InvitationUserErrorResponseModel.INVITING_ORGANIZATION_NOT_FOUND
        )
    }

    @Test
    fun test() {
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val expectedInvitationUuid1 = resourceTestHelper.persistInvitationUserToOrganization(organizationUuid = organizationUuid)
        val expectedInvitationUuid2 = resourceTestHelper.persistInvitationUserToOrganization(organizationUuid = organizationUuid)
        val unexpectedInvitationUserUuid1 = resourceTestHelper.persistInvitationUserToOrganization()
        val unexpectedInvitationUserUuid2 = resourceTestHelper.persistInvitationUserToOrganization()
        resourceTestHelper.updateInvitationStatus(uuid = unexpectedInvitationUserUuid1, status = InvitationStatusModel.REJECTED)
        resourceTestHelper.updateInvitationStatus(uuid = expectedInvitationUuid1, status = InvitationStatusModel.ACCEPTED)
        resourceTestHelper.updateInvitationStatus(uuid = expectedInvitationUuid2, status = InvitationStatusModel.ACCEPTED)
        val request = resourceTestHelper.buildGetAllByStatusInvitationUserRequest(
                invitationStatus = InvitationStatusModel.ACCEPTED,
                organizationUuid = organizationUuid
        )
        val responseEntity = invitationUserResourceClient.getAllInvitationsToOrganizationByStatus(request)
        assertBasicSuccessResultResponse(responseEntity)
        responseEntity?.body?.response()?.let {
            val uuids = it.items().map { model -> model.uuid }.toList()
            assertThat(uuids).contains(expectedInvitationUuid1, expectedInvitationUuid2)
            assertThat(uuids).doesNotContain(unexpectedInvitationUserUuid1, unexpectedInvitationUserUuid2)
        }
    }
}