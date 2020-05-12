package com.vntana.core.service.invitation.user.impl

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.service.invitation.user.AbstractInvitationUserServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/12/2020
 * Time: 3:10 PM
 */
class InvitationUserGetByEmailAndOrganizationUuidAndStatusServiceUnitTest : AbstractInvitationUserServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { commonTestHelper.buildGetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(email = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildGetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(email = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildGetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(organizationUuid = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildGetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(organizationUuid = emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { commonTestHelper.buildGetAllInvitationUsersByEmailAndOrganizationUuidAndStatusDto(status = null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        assertThatThrownBy { invitationUserService.getByInviterUserUuid(emptyString()) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }
    
    @Test
    fun test() {
        val organization = organizationCommonTestHelper.buildOrganization()
        val email = uuid()
        val invitationUser1 = commonTestHelper.buildInvitationUser(organization = organization, email = email)
        val invitationUser2 = commonTestHelper.buildInvitationUser(organization = organization, email = email)
        val invitationUser3 = commonTestHelper.buildInvitationUser(organization = organization, email = email)
        resetAll()
        expect(invitationUserRepository.findByEmailAndOrganizationUuidAndStatusOrderByCreatedDesc(email, organization.uuid, InvitationStatus.INVITED)).andReturn(listOf(invitationUser3, invitationUser2, invitationUser1))
        replayAll()
        invitationUserService.getByEmailAndOrganizationUuidAndStatusOrderByCreatedDesc(
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