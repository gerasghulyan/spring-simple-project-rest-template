package com.vntana.core.service.user.impl

import com.vntana.core.service.user.AbstractUserServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test
import java.util.*

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 4:40 PM
 */
class UserFindByEmailAndOrganizationUuidServiceUnitTest : AbstractUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { userService.findByEmailAndOrganizationUuid(null, uuid()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.findByEmailAndOrganizationUuid(emptyString(), uuid()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.findByEmailAndOrganizationUuid(uuid(), null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { userService.findByEmailAndOrganizationUuid(uuid(), emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }
    
    @Test
    fun `test not found`() {
        val email = email()
        val organization = organizationHelper.buildOrganization()
        resetAll()
        expect(userRepository.findByEmailAndOrganizationUuid(email, organization.uuid)).andReturn(Optional.empty())
        replayAll()
        assertThat(userService.findByEmailAndOrganizationUuid(email, organization.uuid).isPresent).isFalse()
        verifyAll()
    }

    @Test
    fun test() {
        val email = email()
        val organization = organizationHelper.buildOrganization()
        val user = helper.buildUserWithOrganizationOwnerRole(email = email, organization = organization)
        resetAll()
        expect(userRepository.findByEmailAndOrganizationUuid(email, organization.uuid)).andReturn(Optional.of(user))
        replayAll()
        userService.findByEmailAndOrganizationUuid(email, organization.uuid).let { u ->
            assertThat(u.isPresent).isTrue()
            assertThat(u.get().email).isEqualTo(email)
        }
        verifyAll()
    }
}