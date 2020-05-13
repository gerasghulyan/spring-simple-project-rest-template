package com.vntana.core.service.invitation.user.impl

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.service.invitation.user.AbstractInvitationUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:44 PM
 */
class InvitationUserUpdateStatusServiceIntegrationTest : AbstractInvitationUserServiceIntegrationTest() {
    @Test
    fun `test update`() {
        val invitationUser = integrationInvitationUserTestHelper.persistInvitationUser(
                inviterUserUuid = userIntegrationTestHelper.persistUser().uuid
        )
        val dto = integrationInvitationUserTestHelper.buildUpdateInvitationUserStatusDto(
                uuid = invitationUser.uuid,
                status = InvitationStatus.REJECTED
        )
        flushAndClear()
        invitationUserService.updateStatus(dto).let {
            assertThat(it.status).isEqualTo(dto.status)
        }
    }
}