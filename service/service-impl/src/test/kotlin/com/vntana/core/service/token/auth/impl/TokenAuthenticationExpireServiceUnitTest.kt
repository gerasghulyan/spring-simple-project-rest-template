package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractTokenAuthenticationServiceUnitTest
import com.vntana.core.service.token.auth.exception.TokenAuthenticationNotFoundForUuidException
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
class TokenAuthenticationExpireServiceUnitTest : AbstractTokenAuthenticationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { tokenAuthenticationService.expire(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenAuthenticationService.expire("") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val authToken = commonTestHelper.buildTokenAuthentication()
        val authTokenUuid = authToken.uuid
        expect(tokenAuthenticationRepository.findByUuid(authTokenUuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { (tokenAuthenticationService.expire(authTokenUuid)) }
                .isExactlyInstanceOf(TokenAuthenticationNotFoundForUuidException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val authToken = commonTestHelper.buildTokenAuthentication()
        val authTokenUuid = authToken.uuid
        expect(tokenAuthenticationRepository.findByUuid(authTokenUuid)).andReturn(Optional.of(authToken))
        replayAll()
        tokenAuthenticationService.expire(authTokenUuid).let {
            assertThat(it.isExpired).isTrue()
        }

        verifyAll()
    }
}