package com.vntana.core.rest.resource.invitation.organization.impl

import com.vntana.core.model.invitation.InvitationStatusModel
import com.vntana.core.model.invitation.organization.error.InvitationOrganizationErrorResponseModel
import com.vntana.core.rest.resource.invitation.organization.AbstractInvitationOrganizationWebTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 4/9/2020
 * Time: 11:33 AM
 */
class InvitationOrganizationUpdateStatusWebTest : AbstractInvitationOrganizationWebTest() {

    @Test
    fun `test with invalid arguments`() {
        assertBasicErrorResultResponse(
                invitationOrganizationResourceClient.updateStatus(resourceTestHelper.buildUpdateInvitationOrganizationStatusRequest(uuid = null)),
                InvitationOrganizationErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                invitationOrganizationResourceClient.updateStatus(resourceTestHelper.buildUpdateInvitationOrganizationStatusRequest(uuid = emptyString())),
                InvitationOrganizationErrorResponseModel.MISSING_UUID
        )
        assertBasicErrorResultResponse(
                invitationOrganizationResourceClient.updateStatus(resourceTestHelper.buildUpdateInvitationOrganizationStatusRequest(status = null)),
                InvitationOrganizationErrorResponseModel.MISSING_INVITATION_STATUS
        )
    }

    @Test
    fun `test update status`() {
        val uuid = resourceTestHelper.persistInvitationOrganization()
        val request = resourceTestHelper.buildUpdateInvitationOrganizationStatusRequest(
                uuid = uuid,
                status = InvitationStatusModel.REJECTED
        )
        assertBasicSuccessResultResponse(invitationOrganizationResourceClient.updateStatus(request))
        resourceTestHelper.fetchGetByUuid(uuid).let {
            assertThat(it?.status).isEqualTo(request.status)
        }
    }
}