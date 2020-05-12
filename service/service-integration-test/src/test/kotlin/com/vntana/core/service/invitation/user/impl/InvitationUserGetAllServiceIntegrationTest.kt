package com.vntana.core.service.invitation.user.impl

import com.vntana.core.service.invitation.user.AbstractInvitationUserServiceIntegrationTest
import com.vntana.core.service.invitation.user.dto.GetAllInvitationUsersDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.stream.Collectors

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:43 PM
 */
class InvitationUserGetAllServiceIntegrationTest : AbstractInvitationUserServiceIntegrationTest() {

    @Test
    fun `test when empty`() {
        invitationUserService.getAll(GetAllInvitationUsersDto(10)).run {
            assertThat(totalElements).isEqualTo(0)
        }
    }

    @Test
    fun test() {
        val invitation1 = integrationInvitationUserTestHelper.persistInvitationUser(
                inviterUserUuid = userIntegrationTestHelper.persistUser().uuid
        )
        val invitation2 = integrationInvitationUserTestHelper.persistInvitationUser(
                inviterUserUuid = userIntegrationTestHelper.persistUser().uuid
        )
        flushAndClear()
        invitationUserService.getAll(GetAllInvitationUsersDto(10)).run {
            assertThat(totalElements).isEqualTo(2)
            val list = get().collect(Collectors.toList())
            assertThat(list).containsExactlyInAnyOrder(invitation1, invitation2)
        }
    }
}