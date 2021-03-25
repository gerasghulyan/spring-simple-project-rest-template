package com.vntana.core.service.token.personalaccess.impl

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
 * Time: 10:21 AM
 */
class PersonalAccessTokenFindByTokenServiceUnitTest : AbstractPersonalAccessTokenServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { personalAccessTokenService.findByToken(null) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when token expired`() {
        val user = userCommonTestHelper.buildUser()
        val expiredToken = commonTestHelper.buildTokenPersonalAccess(token = uuid(), user = user)
        expiredToken.uuid = uuid()
        expiredToken.expire()
        resetAll()
        expect(tokenPersonalAccessRepository.findByToken(eq(expiredToken.token)))
            .andReturn(
                Optional.empty()
            )
        replayAll()
        personalAccessTokenService.findByToken(expiredToken.token).let {
            assertThat(it.isPresent).isFalse()
        }
        verifyAll()
    }

    @Test
    fun test() {
        val user = userCommonTestHelper.buildUser()
        val token = commonTestHelper.buildTokenPersonalAccess(token = uuid(), user = user)
        token.uuid = uuid()
        resetAll()
        expect(tokenPersonalAccessRepository.findByToken(eq(token.token)))
            .andReturn(
                Optional.of(token)
            )
        replayAll()
        personalAccessTokenService.findByToken(token.token).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.get()).isEqualTo(token)
        }
        verifyAll()
    }
}