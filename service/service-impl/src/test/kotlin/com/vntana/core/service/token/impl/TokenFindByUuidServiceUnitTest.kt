package com.vntana.core.service.token.impl

import com.vntana.core.service.token.AbstractTokenServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/22/19
 * Time: 12:43 PM
 */
class TokenFindByUuidServiceUnitTest : AbstractTokenServiceUnitTest() {
    @Test
    fun `test findByUuid with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { tokenService.findByUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test findByUuid when not found`() {
        // test data
        resetAll()
        val uuid = uuid()
        // expectations
        expect(tokenRepository.findByUuid(uuid)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        assertThat(tokenService.findByUuid(uuid)).isEmpty
        verifyAll()
    }

    @Test
    fun `test findByUuid`() {
        // test data
        val uuid = uuid()
        val token = helper.buildResetPasswordToken()
        resetAll()
        // expectations
        expect(tokenRepository.findByUuid(uuid)).andReturn(Optional.of(token))
        replayAll()
        // test scenario
        assertThat(tokenService.findByUuid(uuid)).hasValue(token)
        verifyAll()
    }
}