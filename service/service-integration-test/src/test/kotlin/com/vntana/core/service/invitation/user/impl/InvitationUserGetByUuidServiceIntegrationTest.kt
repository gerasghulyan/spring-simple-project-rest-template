package com.vntana.core.service.invitation.user.impl

import com.vntana.core.service.invitation.user.AbstractInvitationUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:43 PM
 */
class InvitationUserGetByUuidServiceIntegrationTest : AbstractInvitationUserServiceIntegrationTest() {
    @Test
    fun test() {
        val invitationUser = integrationInvitationUserTestHelper.persistInvitationUser(
                inviterUserUuid = userIntegrationTestHelper.persistUserWithOwnerRole().uuid
        )
        flushAndClear()
        assertThat(invitationUserService.getByUuid(invitationUser.uuid)).isEqualTo(invitationUser)
    }
}