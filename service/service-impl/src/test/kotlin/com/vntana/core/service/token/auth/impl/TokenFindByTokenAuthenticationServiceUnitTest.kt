package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractTokenAuthenticationServiceUnitTest
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
class TokenFindByTokenAuthenticationServiceUnitTest : AbstractTokenAuthenticationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        Assertions.assertThatThrownBy { tokenAuthenticationService.findByToken(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { tokenAuthenticationService.findByToken("") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val token = uuid()
        val authToken = commonTestHelper.buildTokenAuthentication(token = token)
        expect(tokenAuthenticationRepository.findByToken(token)).andReturn(Optional.of(authToken))
        replayAll()
        tokenAuthenticationService.findByToken(token).let {
            assertThat(it).isPresent
            assertThat(it.get()).isEqualTo(authToken)
        }
        verifyAll()
    }
}