package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractAuthTokenServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 4:34 PM
 */
class AuthTokenFindActiveTokensByUserServiceIntegrationTest : AbstractAuthTokenServiceIntegrationTest() {

    @Test
    fun `test when noting was found`() {
        assertThat(authTokenService.findActiveTokensByUser(uuid())).isEmpty()
    }

    @Test
    fun test() {
        val user1 = userIntegrationTestHelper.persistUser()
        val user2 = userIntegrationTestHelper.persistUser()
        val activeToken1 = integrationTestHelper.persistAuthToken(userUuid = user1.uuid)
        integrationTestHelper.persistAuthToken(userUuid = user1.uuid).expire()
        integrationTestHelper.persistAuthToken(userUuid = user2.uuid).expire()
        val activeToken2 = integrationTestHelper.persistAuthToken(userUuid = user1.uuid)
        val activeToken3 = integrationTestHelper.persistAuthToken(userUuid = user2.uuid)
        flushAndClear()
        assertThat(authTokenService.findActiveTokensByUser(user1.uuid)).containsOnly(activeToken1, activeToken2)
        assertThat(authTokenService.findActiveTokensByUser(user2.uuid)).containsOnly(activeToken3)
    }
}