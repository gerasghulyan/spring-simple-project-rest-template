package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/18/2020
 * Time: 10:05 AM
 */
class TokenInvitationUserFindByInvitationUserUuidServiceIntegrationTest : AbstractTokenInvitationUserServiceIntegrationTest() {

    @Test
    fun `test not found`() {
        assertThat(tokenInvitationUserService.findByInvitationUserUuid(uuid()).isPresent).isFalse()
    }

    @Test
    fun test() {
        val invitationUser = invitationUserIntegrationTestHelper.persistInvitationUser()
        val tokenInvitationUser = integrationTestHelper.persistTokenInvitationUser(invitationUserUuid = invitationUser.uuid)
        flushAndClear()
        val optional = tokenInvitationUserService.findByInvitationUserUuid(invitationUser.uuid)
        assertThat(optional.isPresent).isTrue()
        assertThat(optional.get()).isEqualTo(tokenInvitationUser)
    }
}