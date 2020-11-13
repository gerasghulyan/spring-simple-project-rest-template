package com.vntana.core.service.token.auth.impl

import com.vntana.core.service.token.auth.AbstractTokenAuthenticationServiceUnitTest
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
class TokenAuthenticationFindByUuidServiceUnitTest : AbstractTokenAuthenticationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { tokenAuthenticationService.findByUuid(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenAuthenticationService.findByUuid("") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val tokenUuid = uuid()
        expect(tokenAuthenticationRepository.findByUuid(tokenUuid)).andReturn(Optional.empty())
        replayAll()
        assertThat(tokenAuthenticationService.findByUuid(tokenUuid)).isEmpty
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val token = commonTestHelper.buildTokenAuthentication()
        val tokenUuid = token.uuid
        expect(tokenAuthenticationRepository.findByUuid(tokenUuid)).andReturn(Optional.of(token))
        replayAll()
        tokenAuthenticationService.findByUuid(tokenUuid).let {
            assertThat(it).isPresent
            assertThat(it.get()).isEqualTo(token)
        }
        verifyAll()
    }
}