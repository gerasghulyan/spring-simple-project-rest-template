package com.vntana.core.service.user.impl

import com.vntana.core.service.user.AbstractUserServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Vardan Aivazian
 * Date: 29.09.2020
 * Time: 14:29
 */
class UserFindByUuidsServiceUnitTest : AbstractUserServiceUnitTest() {
    
    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertIllegalArgumentException { userService.findByUuids(null) }
        assertIllegalArgumentException { userService.findByUuids(setOf()) }
        verifyAll()
    }

    @Test
    fun `test when not found`() {
        resetAll()
        val uuids = setOf(uuid(), uuid())
        expect(userRepository.findByUuidIn(uuids)).andReturn(setOf())
        replayAll()
        assertThat(userService.findByUuids(uuids)).isEmpty()
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val user1 = helper.buildUser()
        val user2 = helper.buildUser()
        val uuids = setOf(user1.uuid, user2.uuid)
        expect(userRepository.findByUuidIn(uuids)).andReturn(setOf(user1, user2))
        replayAll()
        assertThat(userService.findByUuids(uuids)).isEqualTo(setOf(user1, user2))
        verifyAll()
    }
}