package com.vntana.core.service.token.impl

import com.vntana.core.service.token.AbstractTokenServiceUnitTest
import com.vntana.core.service.token.exception.TokenNotFoundForUuidException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 6:02 PM
 */
class TokenGetByUuidServiceUnitTest : AbstractTokenServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { tokenService.getByUuid(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenService.getByUuid("") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val tokenUuid = uuid()
        expect(tokenRepository.findByUuid(tokenUuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { (tokenService.getByUuid(tokenUuid)) }
                .isExactlyInstanceOf(TokenNotFoundForUuidException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val token = helper.buildResetPasswordToken()
        val tokenUuid = token.uuid
        expect(tokenRepository.findByUuid(tokenUuid)).andReturn(Optional.of(token))
        replayAll()
        assertThat(tokenService.getByUuid(tokenUuid)).isEqualTo(token)
        verifyAll()
    }
}