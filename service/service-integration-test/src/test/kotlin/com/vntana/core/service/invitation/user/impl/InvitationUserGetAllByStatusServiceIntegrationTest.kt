package com.vntana.core.service.invitation.user.impl

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.service.invitation.user.AbstractInvitationUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.stream.Collectors

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:43 PM
 */
class InvitationUserGetAllByStatusServiceIntegrationTest : AbstractInvitationUserServiceIntegrationTest() {

    @Test
    fun `test when empty`() {
        invitationUserService.getAllByStatus(integrationInvitationUserTestHelper.buildGetAllByStatusInvitationUsersDto()).run {
            assertThat(totalElements).isEqualTo(0)
        }
    }

    @Test
    fun test() {
        val invitation1 = integrationInvitationUserTestHelper.persistInvitationUser(
                inviterUserUuid = userIntegrationTestHelper.persistUserWithOwnerRole().uuid
        )
        val invitation2 = integrationInvitationUserTestHelper.persistInvitationUser(
                inviterUserUuid = userIntegrationTestHelper.persistUserWithOwnerRole().uuid
        )
        val invitation3 = integrationInvitationUserTestHelper.persistInvitationUser(
                inviterUserUuid = userIntegrationTestHelper.persistUserWithOwnerRole().uuid
        )
        integrationInvitationUserTestHelper.updateInvitationUserStatus(uuid = invitation3.uuid, status = InvitationStatus.REJECTED)
        flushAndClear()
        invitationUserService.getAllByStatus(integrationInvitationUserTestHelper.buildGetAllByStatusInvitationUsersDto()).run {
            assertThat(totalElements).isEqualTo(2)
            val list = get().collect(Collectors.toList())
            assertThat(list).containsExactlyInAnyOrder(invitation1, invitation2)
            assertThat(list).doesNotContain(invitation3)
        }
    }
}