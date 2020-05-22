package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 2:31 PM
 */
class TokenInvitationUserFindByTokenServiceIntegrationTest : AbstractTokenInvitationUserServiceIntegrationTest() {

    @Test
    fun `test findByToken not found`() {
        assertThat(tokenInvitationUserService.findByToken(uuid())).isNotPresent
    }

    @Test
    fun `test findByToken`() {
        val token = uuid()
        val invitationUser = invitationUserIntegrationTestHelper.persistInvitationUser()
        val tokenInvitationUser = integrationTestHelper.persistTokenInvitationUser(token = token, invitationUserUuid = invitationUser.uuid)
        flushAndClear()
        tokenInvitationUserService.findByToken(token).let {
            assertThat(it).isPresent
            assertThat(it.get()).isEqualTo(tokenInvitationUser)
        }
    }
}