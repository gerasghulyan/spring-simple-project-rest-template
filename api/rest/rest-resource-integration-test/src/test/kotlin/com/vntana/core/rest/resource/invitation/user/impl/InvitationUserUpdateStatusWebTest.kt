package com.vntana.core.rest.resource.invitation.user.impl

import com.vntana.core.model.invitation.InvitationStatusModel
import com.vntana.core.model.invitation.user.error.InvitationUserErrorResponseModel
import com.vntana.core.rest.resource.invitation.user.AbstractInvitationUserWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.springframework.http.HttpStatus

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 6:35 PM
 */
class InvitationUserUpdateStatusWebTest : AbstractInvitationUserWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.updateStatus(resourceTestHelper.buildUpdateInvitationUserInvitationStatusRequest(uuid = null)),
                InvitationUserErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.updateStatus(resourceTestHelper.buildUpdateInvitationUserInvitationStatusRequest(uuid = emptyString())),
                InvitationUserErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.updateStatus(resourceTestHelper.buildUpdateInvitationUserInvitationStatusRequest(status = null)),
                InvitationUserErrorResponseModel.MISSING_INVITATION_STATUS
        )
    }

    @Test
    fun `test when invitation not found`() {
        assertBasicErrorResultResponse(
                HttpStatus.NOT_FOUND,
                invitationUserResourceClient.updateStatus(resourceTestHelper.buildUpdateInvitationUserInvitationStatusRequest()),
                InvitationUserErrorResponseModel.INVITATION_NOT_FOUND
        )
    }

    @Test
    fun test() {
        val inviterUserUuid = userResourceTestHelper.persistUser().response().uuid
        val organizationUuid = organizationResourceTestHelper.persistOrganization().response().uuid
        val invitationUuid = resourceTestHelper.persistInvitationUser(
                inviterUserUuid = inviterUserUuid,
                organizationUuid = organizationUuid
        ).body?.response()?.uuid
        val responseEntity = invitationUserResourceClient.updateStatus(resourceTestHelper.buildUpdateInvitationUserInvitationStatusRequest(uuid = invitationUuid, status = InvitationStatusModel.REJECTED))
        assertBasicSuccessResultResponse(responseEntity)
        assertThat(responseEntity.body?.response()?.uuid).isNotBlank()
    }
}