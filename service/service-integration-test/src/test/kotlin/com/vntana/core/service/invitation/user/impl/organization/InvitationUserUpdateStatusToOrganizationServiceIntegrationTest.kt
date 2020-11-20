package com.vntana.core.service.invitation.user.impl.organization

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.service.invitation.user.AbstractInvitationUserToOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:44 PM
 */
class InvitationUserUpdateStatusToOrganizationServiceIntegrationTest : AbstractInvitationUserToOrganizationServiceIntegrationTest() {
    @Test
    fun `test update`() {
        val invitationUser = integrationInvitationUserTestHelper.persistInvitationUserToOrganization(
                inviterUserUuid = userIntegrationTestHelper.persistUserWithOwnerRole().uuid
        )
        val dto = integrationInvitationUserTestHelper.buildUpdateInvitationUserStatusDto(
                uuid = invitationUser.uuid,
                status = InvitationStatus.REJECTED
        )
        flushAndClear()
        invitationUserToOrganizationService.updateStatus(dto).let {
            assertThat(it.status).isEqualTo(dto.status)
        }
    }
}