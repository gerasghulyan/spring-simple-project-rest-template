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
        assertThatThrownBy { userService.create(helper.buildUserCreateDto(clientOrganizationUuid = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.create(helper.buildUserCreateDto(role = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test create when client organization not exists`() {
        val createDto = helper.buildUserCreateDto()
        resetAll()
        expect(clientOrganizationService.findByUuid(createDto.clientOrganizationUuid)).andReturn(Optional.empty())
        replayAll()
        assertThatThrownBy { userService.create(createDto) }.isExactlyInstanceOf(IllegalStateException::class.java)
        verifyAll()
    }

    @Test
    fun `test create`() {
        val createDto = helper.buildUserCreateDto()
        val clientOrganization = clientOrganizationHelper.buildClientOrganization()
        resetAll()
        expect(clientOrganizationService.findByUuid(createDto.clientOrganizationUuid)).andReturn(Optional.of(clientOrganization))
        expect(userRepository.save(isA(User::class.java))).andAnswer { getCurrentArguments()[0] as User }
        replayAll()
        userService.create(createDto).let {
            assertThat(it).isNotNull
            assertThat(it.fullName).isEqualTo(createDto.fullName)
            assertThat(it.email).isEqualTo(createDto.email)
            val role = it.roleOfClient(clientOrganization).get()
            assertThat(role.clientOrganization).isEqualTo(clientOrganization)
            assertThat(role.user).isEqualTo(it)
        }
        verifyAll()
    }
}