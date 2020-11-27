package com.vntana.core.service.invitation.user.impl.client

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.service.invitation.user.AbstractInvitationUserToClientServiceIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/27/20
 * Time: 9:36 AM
 */
class UpdateStatusUserInvitationServiceIntegrationTest : AbstractInvitationUserToClientServiceIntegrationTest() {

    @Test
    fun `test update`() {
        val invitationUser = integrationInvitationUserTestHelper.persistUserInvitationToClient()
        val dto = integrationInvitationUserTestHelper.buildUpdateInvitationUserStatusDto(
                uuid = invitationUser.uuid,
                status = InvitationStatus.REJECTED
        )
        flushAndClear()
        invitationUserToClientService.updateStatus(dto).let {
            Assertions.assertThat(it.status).isEqualTo(dto.status)
        }
    }
}