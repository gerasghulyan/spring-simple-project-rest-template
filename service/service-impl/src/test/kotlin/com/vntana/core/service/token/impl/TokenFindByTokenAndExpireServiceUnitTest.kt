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
 * Time: 3:22 PM
 */
class TokenFindByTokenAndExpireServiceUnitTest : AbstractTokenServiceUnitTest(){

    @Test
    fun `test findByTokenAndExpire with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { tokenService.findByTokenAndExpire(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenService.findByTokenAndExpire(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test findByTokenAndExpire when not found`() {
        // test data
        resetAll()
        val token = uuid()
        // expectations
        expect(tokenRepository.findByToken(token)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        assertThatThrownBy { tokenService.findByTokenAndExpire(token) }.isExactlyInstanceOf(TokenNotFoundException::class.java)
        verifyAll()
    }

    @Test
    fun `test findByTokenAndExpire`() {
        // test data
        val token = uuid()
        val abstractToken = helper.buildTokenInvitationOrganization()
        resetAll()
        // expectations
        expect(tokenService.findByToken(token)).andReturn(Optional.of(abstractToken))
        replayAll()
        // test scenario
        assertThat(tokenService.findByTokenAndExpire(token)).isEqualTo(abstractToken)
        verifyAll()
    }
}