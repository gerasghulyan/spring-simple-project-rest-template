package com.vntana.core.service.user.impl

import com.vntana.core.domain.user.User
import com.vntana.core.service.user.AbstractUserServiceUnitTest
import com.vntana.core.service.user.exception.UserNotFoundForUuidException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test
import java.util.*

/**
 * Created by Arman Gevorgyan.
 * Date: 12/12/19
 * Time: 5:49 PM
 */
class UserChangePasswordServiceUnitTest : AbstractUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { userService.changePassword(null, uuid()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.changePassword("", uuid()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.changePassword(uuid(), null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.changePassword(uuid(), "") }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when user not found`() {
        val userUuid = uuid()
        resetAll()
        expect(userRepository.findByUuid(userUuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { userService.changePassword(userUuid, uuid()) }
                .isExactlyInstanceOf(UserNotFoundForUuidException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        val user = helper.buildUser()
        val newPassword = uuid()
        val encodedPassword = uuid()
        resetAll()
        expect(userRepository.findByUuid(user.uuid)).andReturn(Optional.of(user))
        expect(passwordEncoder.encode(newPassword)).andReturn(encodedPassword)
        expect(userRepository.save(isA(User::class.java))).andAnswer { getCurrentArguments()[0] as User }
        replayAll()
        userService.changePassword(user.uuid, newPassword).let {
            assertThat(it.uuid).isEqualTo(user.uuid)
            assertThat(it.password).isEqualTo(encodedPassword)
        }
        verifyAll()
    }
}