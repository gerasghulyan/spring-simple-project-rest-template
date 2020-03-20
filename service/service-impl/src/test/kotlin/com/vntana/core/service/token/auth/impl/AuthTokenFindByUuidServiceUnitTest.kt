package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractAuthTokenServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 3:59 PM
 */
class AuthTokenFindByUuidServiceUnitTest : AbstractAuthTokenServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { authTokenService.findByUuid(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { authTokenService.findByUuid("") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val tokenUuid = uuid()
        expect(authTokenRepository.findByUuid(tokenUuid)).andReturn(Optional.empty())
        replayAll()
        assertThat(authTokenService.findByUuid(tokenUuid)).isEmpty
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val token = commonTestHelper.buildAuthToken()
        val tokenUuid = token.uuid
        expect(authTokenRepository.findByUuid(tokenUuid)).andReturn(Optional.of(token))
        replayAll()
        authTokenService.findByUuid(tokenUuid).let {
            assertThat(it).isPresent
            assertThat(it.get()).isEqualTo(token)
        }
        verifyAll()
    }
}