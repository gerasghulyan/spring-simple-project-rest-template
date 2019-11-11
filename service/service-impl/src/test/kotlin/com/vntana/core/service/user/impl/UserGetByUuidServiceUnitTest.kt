package com.vntana.core.service.user.impl

import com.vntana.core.service.user.AbstractUserServiceUnitTest
import com.vntana.core.service.user.exception.UserNotFoundForUuidException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Geras Ghulyan.
 * Date: 10/21/19
 * Time: 1:00 PM
 */
class UserGetByUuidServiceUnitTest : AbstractUserServiceUnitTest() {
    @Test
    fun `test getByUuid with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { userService.getByUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test getByUuid when not found`() {
        // test data
        resetAll()
        val uuid = uuid()
        // expectations
        expect(userRepository.findByUuid(uuid)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        assertThatThrownBy { userService.getByUuid(uuid) }.isExactlyInstanceOf(UserNotFoundForUuidException::class.java)
        verifyAll()
    }

    @Test
    fun `test getByUuid when found`() {
        // test data
        resetAll()
        val uuid = uuid()
        // expectations
        val user = helper.buildUser()
        expect(userRepository.findByUuid(uuid)).andReturn(Optional.of(user))
        replayAll()
        // test scenario
        assertThat(userService.getByUuid(uuid)).isEqualTo(user)
        verifyAll()
    }
}