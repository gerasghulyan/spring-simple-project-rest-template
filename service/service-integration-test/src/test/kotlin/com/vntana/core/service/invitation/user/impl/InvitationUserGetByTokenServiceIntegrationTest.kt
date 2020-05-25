package com.vntana.core.service.invitation.user.impl

import com.vntana.core.service.invitation.user.AbstractInvitationUserServiceIntegrationTest
import com.vntana.core.service.invitation.user.exception.InvitationUserNotFoundForTokenException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 8:23 PM
 */
class InvitationUserGetByTokenServiceIntegrationTest : AbstractInvitationUserServiceIntegrationTest() {

    @Test
    fun `test not found`() {
        assertThatThrownBy { invitationUserService.getByToken(uuid()) }
                .isExactlyInstanceOf(InvitationUserNotFoundForTokenException::class.java)
    }

    @Test
    fun test() {
        val invitationUser = integrationInvitationUserTestHelper.persistInvitationUser()
        val token = tokenInvitationUserIntegrationTestHelper.persistTokenInvitationUser(invitationUserUuid = invitationUser.uuid).token
        flushAndClear()
        invitationUserService.getByToken(token).let {
            assertThat(it).isNotNull
            assertThat(it).isEqualTo(invitationUser)
        }
    }
}