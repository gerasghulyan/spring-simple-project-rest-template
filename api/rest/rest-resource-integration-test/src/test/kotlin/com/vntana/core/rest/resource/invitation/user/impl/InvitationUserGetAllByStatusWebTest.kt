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
class InvitationUserGetAllByStatusWebTest : AbstractInvitationUserWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                HttpStatus.UNPROCESSABLE_ENTITY,
                invitationUserResourceClient.getAllByStatus(resourceTestHelper.buildGetAllByStatusInvitationUserRequest(invitationStatus = null)),
                InvitationUserErrorResponseModel.MISSING_INVITATION_STATUS
        )
    }

    @Test
    fun test() {
        resourceTestHelper.persistInvitationUser()
        resourceTestHelper.persistInvitationUser()
        val invitationUserUuid = resourceTestHelper.persistInvitationUser().body?.response()?.uuid
        resourceTestHelper.updateInvitationStatus(uuid = invitationUserUuid, status = InvitationStatusModel.REJECTED)
        val request = resourceTestHelper.buildGetAllByStatusInvitationUserRequest()
        val responseEntity = invitationUserResourceClient.getAllByStatus(request)
        assertBasicSuccessResultResponse(responseEntity)
        assertThat(responseEntity.body?.response()?.totalCount()).isEqualTo(2)
    }
}