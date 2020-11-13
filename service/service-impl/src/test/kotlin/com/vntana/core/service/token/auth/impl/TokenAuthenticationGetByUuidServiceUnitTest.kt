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
 * Time: 4:14 PM
 */
class TokenAuthenticationGetByUuidServiceUnitTest : AbstractTokenAuthenticationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { tokenAuthenticationService.getByUuid(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenAuthenticationService.getByUuid("") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val tokenUuid = uuid()
        expect(tokenAuthenticationRepository.findByUuid(tokenUuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { (tokenAuthenticationService.getByUuid(tokenUuid)) }
                .isExactlyInstanceOf(TokenAuthenticationNotFoundForUuidException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val token = commonTestHelper.buildTokenAuthentication()
        val tokenUuid = token.uuid
        expect(tokenAuthenticationRepository.findByUuid(tokenUuid)).andReturn(Optional.of(token))
        replayAll()
        assertThat(tokenAuthenticationService.getByUuid(tokenUuid)).isEqualTo(token)
        verifyAll()
    }
}