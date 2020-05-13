package com.vntana.core.service.invitation.user.impl

import com.vntana.core.domain.invitation.user.InvitationUser
import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.invitation.user.AbstractInvitationUserServiceUnitTest
import com.vntana.core.service.invitation.user.exception.IncorrectUserInvitedRoleOnOrganizationException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:27 PM
 */
class InvitationUserCreateServiceUnitTest : AbstractInvitationUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserDto(userRole = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserDto(email = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserDto(email = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserDto(inviterUserUuid = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserDto(inviterUserUuid = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserDto(organizationUuid = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserDto(organizationUuid = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { invitationUserService.create(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test with incorrect role`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationUserService.create(commonTestHelper.buildCreateInvitationUserDto(userRole = UserRole.ORGANIZATION_OWNER)) }
                .isExactlyInstanceOf(IncorrectUserInvitedRoleOnOrganizationException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val inviterUser = userCommonTestHelper.buildUserWithOrganizationOwnerRole()
        val organization = organizationCommonTestHelper.buildOrganization()
        val dto = commonTestHelper.buildCreateInvitationUserDto(inviterUserUuid = inviterUser.uuid)
        val invitationUser = commonTestHelper.buildInvitationUser(
                userRole = dto.userRole,
                email = dto.email,
                invitedByUser = inviterUser
        )
        resetAll()
        expect(userService.getByUuid(dto.inviterUserUuid)).andReturn(inviterUser)
        expect(organizationService.getByUuid(dto.organizationUuid)).andReturn(organization)
        expect(invitationUserRepository.save(EasyMock.isA(InvitationUser::class.java))).andReturn(invitationUser)
        replayAll()
        assertThat(invitationUserService.create(dto)).isEqualTo(invitationUser)
        verifyAll()
    }
}