package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/27/20
 * Time: 11:31 AM
 */
class TokenDoesUSerInvitationExistIntegrationTest : AbstractTokenInvitationUserServiceIntegrationTest() {

    @Test
    fun `test when does not exist`() {
        Assertions.assertThat(tokenInvitationUserService.doesInvitationToClientExist(uuid())).isFalse()
    }

    @Test
    fun test() {
        val tokenInvitationUser = integrationTestHelper.persistTokenInvitationUserToClient()
        flushAndClear()
        Assertions.assertThat(tokenInvitationUserService.doesInvitationToClientExist(tokenInvitationUser.token)).isTrue()
    }
}