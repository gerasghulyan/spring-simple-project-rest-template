package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractTokenAuthenticationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test
import java.time.LocalDateTime

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 4:31 PM
 */
class TokenAuthenticationFindActiveTokensByUserServiceUnitTest : AbstractTokenAuthenticationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { tokenAuthenticationService.findActiveTokensByUser(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenAuthenticationService.findActiveTokensByUser("") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val userUuid = uuid()
        val authToken = commonTestHelper.buildTokenAuthentication()
        expect(tokenAuthenticationRepository.findByUserUuidAndExpirationIsAfter(eq(userUuid), isA(LocalDateTime::class.java))).andReturn(listOf(authToken))
        replayAll()
        assertThat(tokenAuthenticationService.findActiveTokensByUser(userUuid)).containsOnly(authToken)
        verifyAll()
    }
}