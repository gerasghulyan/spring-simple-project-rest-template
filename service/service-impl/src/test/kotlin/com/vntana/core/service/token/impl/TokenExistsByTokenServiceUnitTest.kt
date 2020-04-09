package com.vntana.core.service.token.impl

import com.vntana.core.service.token.AbstractTokenServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 4/9/2020
 * Time: 2:45 PM
 */
class TokenExistsByTokenServiceUnitTest : AbstractTokenServiceUnitTest() {

    @Test
    fun `test existsByToken with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { tokenService.existsByToken(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenService.existsByToken(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test existsByToken when not found`() {
        // test data
        resetAll()
        val token = uuid()
        // expectations
        expect(tokenRepository.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        assertThat(tokenService.existsByToken(token)).isFalse()
        verifyAll()
    }

    @Test
    fun `test existsByToken`() {
        // test data
        val token = uuid()
        val abstractToken = helper.buildTokenInvitationOrganization()
        resetAll()
        // expectations
        expect(tokenRepository.findByToken(token)).andReturn(Optional.of(abstractToken))
        replayAll()
        // test scenario
        assertThat(tokenService.existsByToken(token)).isTrue()
        verifyAll()
    }
}