package com.bntana.core.service.user.impl.test

import com.bntana.core.service.user.impl.AbstractUserServiceUnitTest
import com.vntana.core.domain.User
import org.assertj.core.api.Assertions
import org.easymock.EasyMock
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/4/19
 * Time: 11:22 AM
 */
class UserCreateServiceUnitTest : AbstractUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        Assertions.assertThatThrownBy { userService.createUser(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { userService.createUser(helper.buildUserCreateDto(firstName = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { userService.createUser(helper.buildUserCreateDto(secondName = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test create`() {
        val createDto = helper.buildUserCreateDto()
        resetAll()
        EasyMock.expect(userRepository.save(EasyMock.isA(User::class.java))).andAnswer { EasyMock.getCurrentArguments()[0] as User }
        replayAll()
        userService.createUser(createDto).let {
            Assertions.assertThat(it).isNotNull
            Assertions.assertThat(it.firstName).isEqualTo(createDto.firstName)
            Assertions.assertThat(it.secondName).isEqualTo(createDto.secondName)
        }
        verifyAll()
    }
}