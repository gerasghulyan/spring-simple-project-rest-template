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
 * Time: 6:04 PM
 */
class TokenExpireServiceUnitTest : AbstractTokenServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { tokenService.expire(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { tokenService.expire("") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val authToken = helper.buildTokenInvitationOrganization()
        val authTokenUuid = authToken.uuid
        expect(tokenRepository.findByUuid(authTokenUuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { (tokenService.expire(authTokenUuid)) }
                .isExactlyInstanceOf(TokenNotFoundForUuidException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val authToken = helper.buildTokenInvitationOrganization()
        val authTokenUuid = authToken.uuid
        expect(tokenRepository.findByUuid(authTokenUuid)).andReturn(Optional.of(authToken))
        replayAll()
        assertThat(tokenService.expire(authTokenUuid).isExpired).isTrue()
        verifyAll()
    }
}