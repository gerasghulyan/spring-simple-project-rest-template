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
 * Time: 4:14 PM
 */
class AuthTokenGetByUuidServiceUnitTest : AbstractAuthTokenServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { authTokenService.getByUuid(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { authTokenService.getByUuid("") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val tokenUuid = uuid()
        expect(authTokenRepository.findByUuid(tokenUuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { (authTokenService.getByUuid(tokenUuid)) }
                .isExactlyInstanceOf(AuthTokenNotFoundForUuidException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val token = commonTestHelper.buildAuthToken()
        val tokenUuid = token.uuid
        expect(authTokenRepository.findByUuid(tokenUuid)).andReturn(Optional.of(token))
        replayAll()
        assertThat(authTokenService.getByUuid(tokenUuid)).isEqualTo(token)
        verifyAll()
    }
}