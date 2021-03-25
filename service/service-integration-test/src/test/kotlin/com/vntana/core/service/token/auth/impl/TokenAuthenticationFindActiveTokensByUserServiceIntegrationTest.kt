package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractTokenAuthenticationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.time.LocalDateTime

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 4:34 PM
 */
class TokenAuthenticationFindActiveTokensByUserServiceIntegrationTest : AbstractTokenAuthenticationServiceIntegrationTest() {

    @Test
    fun `test when noting was found`() {
        assertThat(tokenAuthenticationService.findActiveTokensByUser(uuid())).isEmpty()
    }

    @Test
    fun test() {
        val user1 = userIntegrationTestHelper.persistUserWithOwnerRole()
        val user2 = userIntegrationTestHelper.persistUserWithOwnerRole()
        val activeToken1 = integrationTestHelper.persistAuthToken(userUuid = user1.uuid)
        integrationTestHelper.persistAuthToken(userUuid = user1.uuid).expiration = LocalDateTime.now()
        integrationTestHelper.persistAuthToken(userUuid = user2.uuid).expiration = LocalDateTime.now()
        val activeToken2 = integrationTestHelper.persistAuthToken(userUuid = user1.uuid)
        val activeToken3 = integrationTestHelper.persistAuthToken(userUuid = user2.uuid)
        flushAndClear()
        assertThat(tokenAuthenticationService.findActiveTokensByUser(user1.uuid)).containsOnly(activeToken1, activeToken2)
        assertThat(tokenAuthenticationService.findActiveTokensByUser(user2.uuid)).containsOnly(activeToken3)
    }
}