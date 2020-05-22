package com.vntana.core.service.user.impl

import com.vntana.core.service.user.AbstractUserServiceUnitTest
import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 1/24/2020
 * Time: 10:49 AM
 */
class UserCheckPasswordServiceUnitTest : AbstractUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { userService.checkPassword(null, uuid()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.checkPassword(StringUtils.EMPTY, uuid()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.checkPassword(uuid(), null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.checkPassword(uuid(), StringUtils.EMPTY) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test check password`() {
        val user = helper.buildUserWithOrganizationOwnerRole()
        val oldPassword = uuid()
        resetAll()
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.of(user))
        expect(passwordEncoder.matches(oldPassword, user.password)).andReturn(true)
        replayAll()
        assertThat(userService.checkPassword(user.uuid, oldPassword)).isTrue()
        verifyAll()
    }

    @Test
    fun `test password mismatch`() {
        val user = helper.buildUserWithOrganizationOwnerRole()
        val oldPassword = uuid()
        resetAll()
        expect(userService.findByUuid(user.uuid)).andReturn(Optional.of(user))
        expect(passwordEncoder.matches(oldPassword, user.password)).andReturn(false)
        replayAll()
        assertThat(userService.checkPassword(user.uuid, oldPassword)).isFalse()
        verifyAll()
    }
}