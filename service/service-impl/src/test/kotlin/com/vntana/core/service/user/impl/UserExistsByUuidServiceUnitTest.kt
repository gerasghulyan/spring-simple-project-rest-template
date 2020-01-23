package com.vntana.core.service.user.impl

import com.vntana.core.service.user.AbstractUserServiceUnitTest
import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 1/23/2020
 * Time: 5:09 PM
 */
class UserExistsByUuidServiceUnitTest : AbstractUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { userService.existsByUuid(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.existsByUuid(StringUtils.EMPTY) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test not exist`() {
        // test data
        val uuid = uuid()
        resetAll()
        // expectations
        expect(userRepository.existsByUuid(uuid)).andReturn(false)
        replayAll()
        // test scenario
        assertThat(userService.existsByUuid(uuid)).isFalse()
        verifyAll()
    }

    @Test
    fun `test exists`() {
        // test data
        val uuid = uuid()
        resetAll()
        // expectations
        expect(userRepository.existsByUuid(uuid)).andReturn(true)
        replayAll()
        // test scenario
        assertThat(userService.existsByUuid(uuid)).isTrue()
        verifyAll()
    }
}