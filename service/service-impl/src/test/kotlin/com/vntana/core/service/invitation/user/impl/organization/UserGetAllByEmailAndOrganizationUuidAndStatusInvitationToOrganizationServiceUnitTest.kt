package com.vntana.core.service.invitation.user.impl.organization

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.service.invitation.user.AbstractUserInvitationToOrganizationServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 3:10 PM
 */
class UserGetAllByEmailAndOrganizationUuidAndStatusInvitationToOrganizationServiceUnitTest : AbstractUserInvitationToOrganizationServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildGetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(email = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildGetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(email = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildGetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(organizationUuid = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildGetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(organizationUuid = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildGetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(status = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { invitationUserToOrganizationService.getAllByInviterUserUuid(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }
    
    @Test
    fun test() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val email = uuid()
        val invitationUser1 = commonTestHelper.buildInvitationUserToOrganization(organization = organization, email = email)
        val invitationUser2 = commonTestHelper.buildInvitationUserToOrganization(organization = organization, email = email)
        val invitationUser3 = commonTestHelper.buildInvitationUserToOrganization(organization = organization, email = email)
        resetAll()
        expect(invitationOrganizationUserRepository.findByEmailAndOrganizationUuidAndStatusOrderByCreatedDesc(email, organization.uuid, InvitationStatus.INVITED)).andReturn(listOf(invitationUser3, invitationUser2, invitationUser1))
        replayAll()
        invitationUserToOrganizationService.getAllByEmailAndOrganizationUuidAndStatusOrderByCreatedDesc(
                commonTestHelper.buildGetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(
                        email = email,
                        organizationUuid = organization.uuid,
                        status = InvitationStatus.INVITED
                )
        ).let { list ->
            assertThat(list.isEmpty()).isFalse()
            assertThat(list).containsExactlyElementsOf(listOf(invitationUser3, invitationUser2, invitationUser1))
        }
        verifyAll()
    }
}