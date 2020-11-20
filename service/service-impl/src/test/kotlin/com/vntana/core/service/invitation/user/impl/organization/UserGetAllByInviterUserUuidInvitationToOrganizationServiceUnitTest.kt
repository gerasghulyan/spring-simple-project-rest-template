package com.vntana.core.service.invitation.user.impl.organization

import com.vntana.core.service.invitation.user.AbstractUserInvitationToOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 2:53 PM
 */
class UserGetAllByInviterUserUuidInvitationToOrganizationServiceUnitTest : AbstractUserInvitationToOrganizationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationUserToOrganizationService.getAllByInviterUserUuid(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { invitationUserToOrganizationService.getAllByInviterUserUuid(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }
    
    @Test
    fun test() {
        val inviterUser = userCommonTestHelper.buildUserWithOrganizationOwnerRole()
        val invitationUser1 = commonTestHelper.buildInvitationUserToOrganization(invitedByUser = inviterUser)
        val invitationUser2 = commonTestHelper.buildInvitationUserToOrganization(invitedByUser = inviterUser)
        val invitationUser3 = commonTestHelper.buildInvitationUserToOrganization(invitedByUser = inviterUser)
        resetAll()
        expect(invitationOrganizationUserRepository.findByInviterUserUuid(inviterUser.uuid)).andReturn(listOf(invitationUser1, invitationUser2, invitationUser3))
        replayAll()
        invitationUserToOrganizationService.getAllByInviterUserUuid(inviterUser.uuid).let { list ->
            assertThat(list.isEmpty()).isFalse()
            assertThat(list).containsExactlyElementsOf(listOf(invitationUser1, invitationUser2, invitationUser3))
        }
        verifyAll()
    }
}