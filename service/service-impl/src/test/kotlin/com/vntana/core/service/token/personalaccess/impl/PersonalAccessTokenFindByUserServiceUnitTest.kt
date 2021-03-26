package com.vntana.core.service.token.personalaccess.impl

import com.vntana.core.service.token.personalaccess.AbstractPersonalAccessTokenServiceUnitTest
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.*
import org.easymock.EasyMock.eq
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 10:10 AM
 */
class PersonalAccessTokenFindByUserServiceUnitTest : AbstractPersonalAccessTokenServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { personalAccessTokenService.findByUser(null) }
            .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when token expired`() {
        val user = userCommonTestHelper.buildUser()
        resetAll()
        expect(userService.getByUuid(eq(user.uuid))).andReturn(user)
        expect(tokenPersonalAccessRepository.findTokenPersonalAccessByUserAndExpirationIsNull(eq(user))).andReturn(
            Optional.empty()
        )
        replayAll()
        personalAccessTokenService.findByUser(user.uuid).let {
            assertThat(it.isPresent).isFalse()
        }
        verifyAll()
    }

    @Test
    fun test() {
        val user = userCommonTestHelper.buildUser()
        val token = commonTestHelper.buildTokenPersonalAccess(token = uuid(), user = user)
        resetAll()
        expect(userService.getByUuid(eq(user.uuid))).andReturn(user)
        expect(tokenPersonalAccessRepository.findTokenPersonalAccessByUserAndExpirationIsNull(eq(user))).andReturn(
            Optional.of(token)
        )
        replayAll()
        personalAccessTokenService.findByUser(user.uuid).let {
            assertThat(it.isPresent).isTrue()
            assertThat(it.get()).isEqualTo(token)
        }
        verifyAll()
    }
}