package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractAuthTokenServiceUnitTest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:11 AM
 */
class AuthTokenFindByTokenServiceUnitTest : AbstractAuthTokenServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        Assertions.assertThatThrownBy { authTokenService.findByToken(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { authTokenService.findByToken("") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val token = uuid()
        val authToken = commonTestHelper.buildAuthToken(token = token)
        expect(authTokenRepository.findByToken(token)).andReturn(Optional.of(authToken))
        replayAll()
        authTokenService.findByToken(token).let {
            assertThat(it).isPresent
            assertThat(it.get()).isEqualTo(authToken)
        }
        verifyAll()
    }
}