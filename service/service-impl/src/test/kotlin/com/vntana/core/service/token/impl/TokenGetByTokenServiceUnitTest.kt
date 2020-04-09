package com.vntana.core.service.token.impl

import com.vntana.core.service.token.AbstractTokenServiceUnitTest
import com.vntana.core.service.token.exception.TokenNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 4/9/2020
 * Time: 2:15 PM
 */
class TokenGetByTokenServiceUnitTest : AbstractTokenServiceUnitTest() {

    @Test
    fun `test getByToken with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { tokenService.getByToken(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenService.getByToken(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test getByToken when not found`() {
        // test data
        resetAll()
        val token = uuid()
        // expectations
        expect(tokenRepository.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        assertThatThrownBy { tokenService.getByToken(token) }.isExactlyInstanceOf(TokenNotFoundException::class.java)
        verifyAll()
    }

    @Test
    fun `test getByToken`() {
        // test data
        val token = uuid()
        val abstractToken = helper.buildTokenInvitationOrganization()
        resetAll()
        // expectations
        expect(tokenRepository.findByToken(token)).andReturn(Optional.of(abstractToken))
        replayAll()
        // test scenario
        assertThat(tokenService.getByToken(token)).isEqualTo(abstractToken)
        verifyAll()
    }
}