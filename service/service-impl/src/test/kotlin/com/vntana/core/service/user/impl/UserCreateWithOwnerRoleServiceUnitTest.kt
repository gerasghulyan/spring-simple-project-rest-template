package com.vntana.core.service.user.impl

import com.vntana.core.domain.user.User
import com.vntana.core.service.user.AbstractUserServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.*
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 10/4/19
 * Time: 11:22 AM
 */
class UserCreateWithOwnerRoleServiceUnitTest : AbstractUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { userService.createWithOwnerRole(null) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.createWithOwnerRole(helper.buildCreateUserWithOwnerRoleDto(fullName = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.createWithOwnerRole(helper.buildCreateUserWithOwnerRoleDto(email = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.createWithOwnerRole(helper.buildCreateUserWithOwnerRoleDto(password = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.createWithOwnerRole(helper.buildCreateUserWithOwnerRoleDto(organizationUuid = null)) }
                .isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test create when client organization not exists`() {
        val createDto = helper.buildCreateUserWithOwnerRoleDto()
        resetAll()
        expect(organizationService.getByUuid(createDto.organizationUuid)).andThrow(IllegalStateException())
        replayAll()
        assertThatThrownBy { userService.createWithOwnerRole(createDto) }.isExactlyInstanceOf(IllegalStateException::class.java)
        verifyAll()
    }

    @Test
    fun `test create`() {
        val createDto = helper.buildCreateUserWithOwnerRoleDto()
        val organization = organizationHelper.buildOrganization()
        val encodedPassword = uuid()
        resetAll()
        expect(organizationService.getByUuid(createDto.organizationUuid)).andReturn(organization)
        expect(passwordEncoder.encode(createDto.password)).andReturn(encodedPassword)
        expect(userRepository.save(isA(User::class.java))).andAnswer { getCurrentArguments()[0] as User }
        replayAll()
        userService.createWithOwnerRole(createDto).let {
            assertThat(it).isNotNull
            assertThat(it.fullName).isEqualTo(createDto.fullName)
            assertThat(it.email).isEqualTo(createDto.email)
            assertThat(it.password).isEqualTo(encodedPassword)
            val role = it.roleOfOrganizationOwner(organization).get()
            assertThat(role.organization).isEqualTo(organization)
            assertThat(role.user).isEqualTo(it)
        }
        verifyAll()
    }
}