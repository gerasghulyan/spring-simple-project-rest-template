package com.vntana.core.service.user.impl

import com.vntana.core.service.user.AbstractUserServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 1:00 PM
 */
class UserFindByUuidServiceUnitTest : AbstractUserServiceUnitTest() {
    @Test
    fun `test with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { userService.findByUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test findByUuid when not found`() {
        // test data
        resetAll()
        val uuid = uuid()
        // expectations
        expect(userRepository.findByUuid(uuid)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        assertThat(userService.findByUuid(uuid)).isEmpty
        verifyAll()
    }

    @Test
    fun `test findByUuid when found`() {
        // test data
        resetAll()
        val uuid = uuid()
        // expectations
        val user = helper.buildUserWithOrganizationOwnerRole()
        expect(userRepository.findByUuid(uuid)).andReturn(Optional.of(user))
        replayAll()
        // test scenario
        assertThat(userService.findByUuid(uuid)).hasValue(user)
        verifyAll()
    }
}