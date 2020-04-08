package com.vntana.core.service.token.impl

import com.vntana.core.service.token.AbstractTokenServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 2:26 PM
 */
class TokenFindByTokenServiceUnitTest : AbstractTokenServiceUnitTest() {
    @Test
    fun `test with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { tokenService.findByToken(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        // test data
        resetAll()
        val token = uuid()
        // expectations
        expect(tokenRepository.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        assertThat(tokenService.findByToken(token)).isEmpty
        verifyAll()
    }

    @Test
    fun `test when found`() {
        // test data
        resetAll()
        val token = uuid()
        val abstractToken = helper.buildTokenInvitationOrganization()
        // expectations
        expect(tokenRepository.findByToken(token)).andReturn(Optional.of(abstractToken))
        replayAll()
        // test scenario
        assertThat(tokenService.findByToken(token)).hasValue(abstractToken)
        verifyAll()
    }
}