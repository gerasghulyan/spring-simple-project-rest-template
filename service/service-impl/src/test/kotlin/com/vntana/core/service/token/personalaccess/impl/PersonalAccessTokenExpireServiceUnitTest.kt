package com.vntana.core.service.token.personalaccess.impl

import com.vntana.core.service.token.exception.TokenNotFoundException
import com.vntana.core.service.token.personalaccess.AbstractPersonalAccessTokenServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.eq
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 9:52 AM
 */
class PersonalAccessTokenExpireServiceUnitTest : AbstractPersonalAccessTokenServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { personalAccessTokenService.expire(null) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when token not found`() {
        val tokenUuid = uuid()
        resetAll()
        expect(tokenPersonalAccessRepository.findByUuid(eq(tokenUuid))).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { personalAccessTokenService.expire(tokenUuid) }
            .isExactlyInstanceOf(TokenNotFoundException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        val token = uuid()
        val user = userCommonTestHelper.buildUser()
        val pat = commonTestHelper.buildTokenPersonalAccess(token = token, user = user)
        pat.uuid = uuid()
        resetAll()
        expect(tokenPersonalAccessRepository.findByUuid(eq(pat.uuid))).andReturn(Optional.of(pat))
        expect(tokenPersonalAccessRepository.save(pat)).andReturn(pat)
        replayAll()
        personalAccessTokenService.expire(pat.uuid).let {
            assertThat(it.token).isEqualTo(token)
            assertThat(it.isExpired).isTrue()
        }
        verifyAll()
    }
}