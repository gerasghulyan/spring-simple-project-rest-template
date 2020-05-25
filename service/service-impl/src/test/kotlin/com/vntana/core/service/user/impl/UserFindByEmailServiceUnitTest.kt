package com.vntana.core.service.user.impl

import com.vntana.core.service.user.AbstractUserServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/18/19
 * Time: 4:56 PM
 */
class UserFindByEmailServiceUnitTest : AbstractUserServiceUnitTest() {
    @Test
    fun `test with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { userService.findByEmail(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        // test data
        val email = uuid()
        resetAll()
        // expectations
        expect(userRepository.findByEmail(email)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        assertThat(userService.findByEmail(email)).isEmpty
        verifyAll()
    }

    @Test
    fun `test when found`() {
        // test data
        val email = uuid()
        val user = helper.buildUserWithOrganizationOwnerRole()
        resetAll()
        // expectations
        expect(userRepository.findByEmail(email)).andReturn(Optional.of(user))
        replayAll()
        // test scenario
        assertThat(userService.findByEmail(email)).isNotEmpty.hasValue(user)
        verifyAll()
    }
}