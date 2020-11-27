package com.vntana.core.service.invitation.user.impl.client

import com.vntana.core.service.invitation.user.AbstractUserInvitationToClientServiceUnitTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.easymock.EasyMock.expect
import org.easymock.EasyMock.or
import org.junit.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

/**
 * Created by Diana Gevorgyan
 * Date: 11/27/20
 * Time: 8:41 AM
 */
class GetAllByOrganizationUuidAndStatusUserInvitationsServiceUnitTest : AbstractUserInvitationToClientServiceUnitTest() {

    @Test
    fun `test with invalid arguments`() {
        resetAll()
        replayAll()
        assertThatThrownBy { invitationUserToClientService.getAllByOrganizationUuidAndStatus(null) }.isExactlyInstanceOf(IllegalArgumentException::class.java)
        verifyAll()
    }

    @Test
    fun test() {
        resetAll()
        val page = 0
        val size = 2
        val dto = commonTestHelper.buildGetAllByOrganizationUuidAndStatusInvitationUsersDto(page, size)

        val organization = organizationCommonTestHelper.buildOrganization()
        val client1 = clientOrganizationCommonTestHelper.buildClientOrganization(organization = organization)
        val client2 = clientOrganizationCommonTestHelper.buildClientOrganization(organization = organization)
        organization.grantClientOrganization(client1)
        organization.grantClientOrganization(client2)

        val userInvitation1 = commonTestHelper.buildInvitationOrganizationClientUser(clientOrganization = client1)
        val userInvitation2 = commonTestHelper.buildInvitationOrganizationClientUser(clientOrganization = client2)
        
        val pageOfInvitations1 = PageImpl(listOf(userInvitation1), commonTestHelper.buildPageRequest(0, size), 1)
        val pageOfInvitations2 = PageImpl(listOf(userInvitation2), commonTestHelper.buildPageRequest(0, size), 1)
        
        expect(organizationService.getByUuid(dto.organizationUuid)).andReturn(organization)
        
        expect(invitationOrganizationClientUserRepository.findAllByClientOrganizationUuidAndStatus(userInvitation1.clientOrganization.uuid, dto.status, PageRequest.of(page, size))).andReturn(pageOfInvitations1)
        expect(invitationOrganizationClientUserRepository.findAllByClientOrganizationUuidAndStatus(userInvitation2.clientOrganization.uuid, dto.status, PageRequest.of(page, size))).andReturn(pageOfInvitations2)
        
        val userInvitations = listOf(userInvitation1, userInvitation2)
        replayAll()
        assertThat(invitationUserToClientService.getAllByOrganizationUuidAndStatus(dto)).isEqualTo(userInvitations)
        verifyAll()
    }
}