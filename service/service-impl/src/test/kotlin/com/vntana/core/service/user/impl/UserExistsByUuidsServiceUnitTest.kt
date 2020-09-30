package com.vntana.core.service.user.impl

import com.vntana.core.service.user.AbstractUserServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 29.09.2020
 * Time: 16:04
 */
class UserExistsByUuidsServiceUnitTest : AbstractUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertIllegalArgumentException { userService.existsByUuids(null) }
        assertIllegalArgumentException { userService.existsByUuids(setOf()) }
        verifyAll()
    }

    @Test
    fun `test not exist`() {
        val uuids = setOf(uuid(), uuid())
        resetAll()
        expect(userRepository.existsAllByUuidIn(uuids)).andReturn(false)
        replayAll()
        assertThat(userService.existsByUuids(uuids)).isFalse()
        verifyAll()
    }

    @Test
    fun `test exists`() {
        val uuids = setOf(uuid(), uuid())
        resetAll()
        expect(userRepository.existsAllByUuidIn(uuids)).andReturn(true)
        replayAll()
        assertThat(userService.existsByUuids(uuids)).isTrue()
        verifyAll()
    }
}