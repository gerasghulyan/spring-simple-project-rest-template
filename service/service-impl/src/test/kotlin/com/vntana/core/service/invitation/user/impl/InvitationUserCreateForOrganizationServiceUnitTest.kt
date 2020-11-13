package com.vntana.core.service.invitation.user.impl

import com.vntana.core.domain.invitation.user.InvitationOrganizationUser
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
class InvitationUserCreateForOrganizationServiceUnitTest : AbstractInvitationUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserForOrganizationDto(userRole = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserForOrganizationDto(email = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserForOrganizationDto(email = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserForOrganizationDto(inviterUserUuid = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserForOrganizationDto(inviterUserUuid = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserForOrganizationDto(organizationUuid = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserForOrganizationDto(organizationUuid = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { invitationUserService.createInvitationForOrganization(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun `test with incorrect role`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationUserService.createInvitationForOrganization(commonTestHelper.buildCreateInvitationUserForOrganizationDto(userRole = UserRole.ORGANIZATION_OWNER)) }
                .isExactlyInstanceOf(IncorrectUserInvitedRoleOnOrganizationException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val inviterUser = userCommonTestHelper.buildUserWithOrganizationOwnerRole()
        val organization = organizationCommonTestHelper.buildOrganization()
        val dto = commonTestHelper.buildCreateInvitationUserForOrganizationDto(inviterUserUuid = inviterUser.uuid)
        val invitationUser = commonTestHelper.buildInvitationUser(
                userRole = dto.userRole,
                email = dto.email,
                invitedByUser = inviterUser
        )
        resetAll()
        expect(userService.getByUuid(dto.inviterUserUuid)).andReturn(inviterUser)
        expect(organizationService.getByUuid(dto.organizationUuid)).andReturn(organization)
        expect(invitationOrganizationUserRepository.save(EasyMock.isA(InvitationOrganizationUser::class.java))).andReturn(invitationUser)
        replayAll()
        assertThat(invitationUserService.createInvitationForOrganization(dto)).isEqualTo(invitationUser)
        verifyAll()
    }
}