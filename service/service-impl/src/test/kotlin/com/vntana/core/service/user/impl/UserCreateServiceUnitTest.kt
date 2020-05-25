package com.vntana.core.service.user.impl

import com.vntana.core.domain.user.User
import com.vntana.core.service.user.AbstractUserServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/15/20
 * Time: 4:26 PM
 */
class UserCreateServiceUnitTest : AbstractUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        assertThatThrownBy { userService.create(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.create(helper.buildCreateUserDto(fullName = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.create(helper.buildCreateUserDto(fullName = emptyString())) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.create(helper.buildCreateUserDto(email = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.create(helper.buildCreateUserDto(email = emptyString())) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.create(helper.buildCreateUserDto(password = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.create(helper.buildCreateUserDto(password = emptyString())) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        replayAll()
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val encodedPassword = uuid()
        val dto = helper.buildCreateUserDto()
        expect(userRepository.save(isA(User::class.java))).andAnswer { getCurrentArguments()[0] as User }
        expect(passwordEncoder.encode(dto.password)).andReturn(encodedPassword)
        replayAll()
        userService.create(dto).let {
            assertThat(it).isNotNull
            assertThat(it.fullName).isEqualTo(dto.fullName)
            assertThat(it.email).isEqualTo(dto.email)
            assertThat(it.password).isEqualTo(encodedPassword)
        }
        verifyAll()
    }
}