package com.vntana.core.service.user.impl

import com.vntana.core.domain.user.User
import com.vntana.core.service.user.AbstractUserServiceUnitTest
import com.vntana.core.service.user.exception.UserAlreadyVerifiedException
import com.vntana.core.service.user.exception.UserNotFoundForUuidException
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
        val userUuid = uuid()
        resetAll()
        expect(userRepository.findByUuid(userUuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { userService.makeVerified(userUuid) }
                .isExactlyInstanceOf(UserNotFoundForUuidException::class.java)
        verifyAll()
    }

    @Test
    fun `test when user is already verified`() {
        val user = helper.buildUser()
        user.verified = true
        val userUuid = user.uuid
        resetAll()
        expect(userRepository.findByUuid(userUuid)).andReturn(Optional.of(user))
        replayAll()
        assertThatThrownBy { userService.makeVerified(userUuid) }
                .isExactlyInstanceOf(UserAlreadyVerifiedException::class.java)
        verifyAll()
    }

    @Test
    fun `test`() {
        val user = helper.buildUser()
        val userUuid = user.uuid
        resetAll()
        expect(userRepository.findByUuid(userUuid)).andReturn(Optional.of(user))
        expect(userRepository.save(isA(User::class.java))).andAnswer { getCurrentArguments()[0] as User }
        replayAll()
        userService.makeVerified(userUuid).let {
            assertThat(it.uuid).isEqualTo(userUuid)
            assertThat(it.verified).isTrue()
        }
        verifyAll()
    }
}