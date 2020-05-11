package com.vntana.core.service.invitation.user.impl

import com.vntana.core.service.invitation.user.AbstractInvitationUserServiceUnitTest
import com.vntana.core.service.user.exception.UserNotFoundForUuidException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 2:53 PM
 */
class InvitationUserFindByInviterUserUuidServiceUnitTest : AbstractInvitationUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationUserService.findByInviterUserUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { invitationUserService.findByInviterUserUuid(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test inviter user not found`() {
        val inviterUserUuid = uuid()
        resetAll()
        expect(userService.existsByUuid(inviterUserUuid)).andReturn(false)
        replayAll()
        assertThatThrownBy { invitationUserService.findByInviterUserUuid(inviterUserUuid) }.isExactlyInstanceOf(UserNotFoundForUuidException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        val inviterUser = userCommonTestHelper.buildUser()
        val invitationUser1 = commonTestHelper.buildInvitationUser(invitedByUser = inviterUser)
        val invitationUser2 = commonTestHelper.buildInvitationUser(invitedByUser = inviterUser)
        val invitationUser3 = commonTestHelper.buildInvitationUser(invitedByUser = inviterUser)
        resetAll()
        expect(userService.existsByUuid(inviterUser.uuid)).andReturn(true)
        expect(invitationUserRepository.findByInviterUserUuid(inviterUser.uuid)).andReturn(listOf(invitationUser1, invitationUser2, invitationUser3))
        replayAll()
        invitationUserService.findByInviterUserUuid(inviterUser.uuid).let { list ->
            assertThat(list.isEmpty()).isFalse()
            assertThat(list).containsExactlyElementsOf(listOf(invitationUser1, invitationUser2, invitationUser3))
        }
        verifyAll()
    }
}