package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractAuthTokenServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 4:31 PM
 */
class AuthTokenFindActiveTokensByUserServiceUnitTest : AbstractAuthTokenServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { authTokenService.findActiveTokensByUser(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { authTokenService.findActiveTokensByUser("") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val userUuid = uuid()
        val authToken = commonTestHelper.buildAuthToken()
        expect(authTokenRepository.findByUserUuidAndExpirationIsNull(userUuid)).andReturn(listOf(authToken))
        replayAll()
        assertThat(authTokenService.findActiveTokensByUser(userUuid)).containsOnly(authToken)
        verifyAll()
    }
}