package com.vntana.core.service.user.impl

import com.vntana.core.domain.user.User
import com.vntana.core.service.user.AbstractUserServiceUnitTest
import com.vntana.core.service.user.exception.UserNotFoundForUuidException
import org.apache.commons.lang3.StringUtils
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 1/23/2020
 * Time: 3:50 PM
 */
class UserUpdateServiceUnitTest : AbstractUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        // test data
        resetAll()
        // expectations
        replayAll()
        // test scenario
        assertThatThrownBy { userService.update(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.update(helper.buildUpdateUserDto(uuid = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.update(helper.buildUpdateUserDto(uuid = StringUtils.EMPTY)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.update(helper.buildUpdateUserDto(fullName = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.update(helper.buildUpdateUserDto(fullName = StringUtils.EMPTY)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test update user not found`() {
        // test data
        val dto = helper.buildUpdateUserDto()
        resetAll()
        // expectations
        expect(userRepository.findByUuid(dto.uuid)).andReturn(Optional.empty())
        replayAll()
        // test scenario
        assertThatThrownBy { userService.update(dto) }
                .isExactlyInstanceOf(UserNotFoundForUuidException::class.java)
    }

    @Test
    fun `test update`() {
        // test data
        val user = helper.buildUserWithOrganizationOwnerRole()
        val dto = helper.buildUpdateUserDto(uuid = user.uuid)
        resetAll()
        // expectations
        expect(userRepository.findByUuid(dto.uuid)).andReturn(Optional.of(user))
        expect(userRepository.save(EasyMock.isA(User::class.java))).andAnswer { EasyMock.getCurrentArguments()[0] as User }
        replayAll()
        // test scenario
        userService.update(dto).let {
            assertThat(it).isNotNull
            assertThat(it.uuid).isEqualTo(dto.uuid)
        }
    }
}