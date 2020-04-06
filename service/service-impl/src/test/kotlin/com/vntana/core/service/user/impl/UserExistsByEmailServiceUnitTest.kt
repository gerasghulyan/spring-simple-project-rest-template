package com.vntana.core.service.user.impl

import com.vntana.core.service.user.AbstractUserServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 4/6/20
 * Time: 10:30 AM
 */
class UserExistsByEmailServiceUnitTest : AbstractUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { userService.existsByEmail(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.existsByEmail(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when exists`() {
        resetAll()
        val email = uuid()
        expect(userRepository.existsByEmail(email)).andReturn(true)
        replayAll()
        assertThat(userService.existsByEmail(email)).isTrue()
        verifyAll()
    }

    @Test
    fun `test when foes not exist`() {
        resetAll()
        val email = uuid()
        expect(userRepository.existsByEmail(email)).andReturn(false)
        replayAll()
        assertThat(userService.existsByEmail(email)).isFalse()
        verifyAll()
    }
}