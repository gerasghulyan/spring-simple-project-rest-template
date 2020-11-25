package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/15/2020
 * Time: 7:47 PM
 */
class TokenInvitationUserIsExistsServiceIntegrationTest : AbstractTokenInvitationUserServiceIntegrationTest() {

    @Test
    fun `test when does not exist`() {
        assertThat(tokenInvitationUserService.isExists(uuid())).isFalse()
    }

    @Test
    fun test() {
        val tokenInvitationUser = integrationTestHelper.persistTokenInvitationUserToOrganization()
        flushAndClear()
        assertThat(tokenInvitationUserService.isExists(tokenInvitationUser.token)).isTrue()
    }
}