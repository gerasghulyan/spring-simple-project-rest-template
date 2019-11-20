package com.vntana.core.service.user.impl

import com.vntana.core.domain.user.User
import com.vntana.core.service.user.AbstractUserServiceUnitTest
import com.vntana.core.service.user.exception.UserAlreadyVerifiedException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 11/11/19
 * Time: 2:30 PM
 */
class UserMakeVerifiedServiceUnitTest : AbstractUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { userService.makeVerified(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.makeVerified("") }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when user does not exist`() {
        val email = uuid()
        resetAll()
        expect(userRepository.findByEmail(email)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { userService.makeVerified(email) }
                .isExactlyInstanceOf(IllegalStateException::class.java)
        verifyAll()
    }

    @Test
    fun `test when user is already verified`() {
        val user = helper.buildUser()
        user.verified = true
        val email = user.email
        resetAll()
        expect(userRepository.findByEmail(email)).andReturn(Optional.of(user))
        replayAll()
        assertThatThrownBy { userService.makeVerified(email) }
                .isExactlyInstanceOf(UserAlreadyVerifiedException::class.java)
        verifyAll()
    }

    @Test
    fun `test`() {
        val user = helper.buildUser()
        val email = user.email
        resetAll()
        expect(userRepository.findByEmail(email)).andReturn(Optional.of(user))
        expect(userRepository.save(isA(User::class.java))).andAnswer { getCurrentArguments()[0] as User }
        replayAll()
        userService.makeVerified(email).let {
            assertThat(it.email).isEqualTo(email)
            assertThat(it.verified).isTrue()
        }
        verifyAll()
    }
}