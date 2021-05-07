package com.vntana.core.service.invitation.user.impl.client

import com.vntana.core.domain.invitation.user.InvitationOrganizationClientUser
import com.vntana.core.domain.user.UserRole
import com.vntana.core.service.invitation.user.AbstractUserInvitationToClientServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.easymock.EasyMock.isA
import org.junit.Test

/**
 * Created by Diana Gevorgyan
 * Date: 11/11/20
 * Time: 8:26 AM
 */
class CreateInvitationUserToClientServiceUnitTest : AbstractUserInvitationToClientServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserForClientsWithContentManagerRoleDto(email = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserForClientsWithContentManagerRoleDto(email = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserForClientsWithContentManagerRoleDto(inviterUserUuid = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserForClientsWithContentManagerRoleDto(inviterUserUuid = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserForClientsWithContentManagerRoleDto(organizationUuid = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildCreateInvitationUserForClientsWithContentManagerRoleDto(organizationUuid = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { invitationUserToClientService.create(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val inviterUser = userCommonTestHelper.buildUserWithOrganizationOwnerRole()
        val organization = organizationCommonTestHelper.buildOrganization()
        val client = clientOrganizationCommonTestHelper.buildClientOrganization(organization = organization)
        val dto = commonTestHelper.buildCreateInvitationUserForClientsWithContentManagerRoleDto(inviterUserUuid = inviterUser.uuid, clientUuid = client.uuid)
        val invitationUser = commonTestHelper.buildInvitationOrganizationClientUser(
                userRole = UserRole.CLIENT_ORGANIZATION_CONTENT_MANAGER,
                email = dto.email,
                inviterUser = inviterUser,
                clientOrganization = client
        )
        resetAll()
        expect(userService.getByUuid(dto.inviterUserUuid)).andReturn(inviterUser)
        expect(clientOrganizationService.getByUuid(client.uuid)).andReturn(client).once()
        expect(invitationOrganizationClientUserRepository.save(isA(InvitationOrganizationClientUser::class.java))).andReturn(invitationUser).once()
        replayAll()
        assertThat(invitationUserToClientService.create(dto)[0]).isEqualTo(invitationUser)
        verifyAll()
    }
}