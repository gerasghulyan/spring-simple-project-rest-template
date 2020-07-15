package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractAuthTokenServiceUnitTest
import com.vntana.core.service.token.auth.exception.AuthTokenNotFoundForUuidException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 4:22 PM
 */
class AuthTokenExpireServiceUnitTest : AbstractAuthTokenServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { authTokenService.expire(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { authTokenService.expire("") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val authToken = commonTestHelper.buildAuthToken()
        val authTokenUuid = authToken.uuid
        expect(authTokenRepository.findByUuid(authTokenUuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { (authTokenService.expire(authTokenUuid)) }
                .isExactlyInstanceOf(AuthTokenNotFoundForUuidException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val authToken = commonTestHelper.buildAuthToken()
        val authTokenUuid = authToken.uuid
        expect(authTokenRepository.findByUuid(authTokenUuid)).andReturn(Optional.of(authToken))
        replayAll()
        authTokenService.expire(authTokenUuid).let {
            assertThat(it.isExpired).isTrue()
        }

        verifyAll()
    }
}