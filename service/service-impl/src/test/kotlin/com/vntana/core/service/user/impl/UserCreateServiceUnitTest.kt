package com.vntana.core.service.user.impl

import com.vntana.core.domain.user.User
import com.vntana.core.service.user.AbstractUserServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test
import java.util.*

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
        assertThatThrownBy { userService.create(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.create(helper.buildUserCreateDto(fullName = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.create(helper.buildUserCreateDto(email = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.create(helper.buildUserCreateDto(password = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.create(helper.buildUserCreateDto(organizationUuid = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.create(helper.buildUserCreateDto(role = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test create when client organization not exists`() {
        val createDto = helper.buildUserCreateDto()
        resetAll()
        expect(organizationService.findByUuid(createDto.organizationUuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { userService.create(createDto) }.isExactlyInstanceOf(IllegalStateException::class.java)
        verifyAll()
    }

    @Test
    fun `test create`() {
        val createDto = helper.buildUserCreateDto()
        val organization = organizationHelper.buildOrganization()
        val encodedPassword = uuid()
        resetAll()
        expect(organizationService.findByUuid(createDto.organizationUuid)).andReturn(Optional.of(organization))
        expect(passwordEncoder.encode(createDto.password)).andReturn(encodedPassword)
        expect(userRepository.save(isA(User::class.java))).andAnswer { getCurrentArguments()[0] as User }
        replayAll()
        userService.create(createDto).let {
            assertThat(it).isNotNull
            assertThat(it.fullName).isEqualTo(createDto.fullName)
            assertThat(it.email).isEqualTo(createDto.email)
            assertThat(it.password).isEqualTo(encodedPassword)
            val role = it.roleOfOrganization(organization).get()
            assertThat(role.organization).isEqualTo(organization)
            assertThat(role.user).isEqualTo(it)
        }
        verifyAll()
    }
}