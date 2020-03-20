package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractAuthTokenServiceUnitTest
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.easymock.EasyMock.isA
import org.junit.Test
import java.time.LocalDateTime

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 5:52 PM
 */
class AuthTokenExpireAllByUserServiceUnitTest : AbstractAuthTokenServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { authTokenService.expireAllByUser(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { authTokenService.expireAllByUser("") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val userUuid = uuid()
        expect(authTokenRepository.updateActiveTokensExpirationByUser(isA(String::class.java), isA(LocalDateTime::class.java)))
        replayAll()
        authTokenService.expireAllByUser(userUuid)
        verifyAll()
    }
}