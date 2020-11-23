package com.vntana.core.service.invitation.user.impl.organization

import com.vntana.core.service.invitation.user.AbstractUserInvitationToOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:43 PM
 */
class UserExistsByUuidInvitationToOrganizationServiceUnitTest : AbstractUserInvitationToOrganizationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationUserToOrganizationService.existsByUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { invitationUserToOrganizationService.existsByUuid(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test when does not exist`() {
        val uuid = uuid()
        resetAll()
        expect(invitationOrganizationUserRepository.existsByUuid(uuid)).andReturn(false)
        replayAll()
        assertThat(invitationUserToOrganizationService.existsByUuid(uuid)).isFalse()
        verifyAll()
    }

    @Test
    fun `test when found`() {
        val uuid = uuid()
        resetAll()
        expect(invitationOrganizationUserRepository.existsByUuid(uuid)).andReturn(true)
        replayAll()
        assertThat(invitationUserToOrganizationService.existsByUuid(uuid)).isTrue()
        verifyAll()
    }
}