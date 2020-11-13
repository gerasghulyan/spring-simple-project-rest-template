package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractTokenAuthenticationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 4:48 PM
 */
class TokenAuthenticationExpireAllByUSerServiceIntegrationTest : AbstractTokenAuthenticationServiceIntegrationTest() {

    @Test
    fun test() {
        val user1 = userIntegrationTestHelper.persistUserWithOwnerRole()
        val user2 = userIntegrationTestHelper.persistUserWithOwnerRole()
        integrationTestHelper.persistAuthToken(userUuid = user1.uuid)
        integrationTestHelper.persistAuthToken(userUuid = user1.uuid).expire()
        integrationTestHelper.persistAuthToken(userUuid = user2.uuid).expire()
        integrationTestHelper.persistAuthToken(userUuid = user1.uuid)
        val activeToken = integrationTestHelper.persistAuthToken(userUuid = user2.uuid)
        flushAndClear()
        tokenAuthenticationService.expireAllByUser(user1.uuid)
        flushAndClear()
        assertThat(tokenAuthenticationService.findActiveTokensByUser(user1.uuid)).isEmpty()
        assertThat(tokenAuthenticationService.findActiveTokensByUser(user2.uuid)).containsOnly(activeToken)
    }
}