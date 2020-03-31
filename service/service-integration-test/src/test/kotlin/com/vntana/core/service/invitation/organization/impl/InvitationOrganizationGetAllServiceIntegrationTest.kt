package com.vntana.core.service.invitation.organization.impl

import com.vntana.core.service.invitation.organization.AbstractInvitationOrganizationServiceIntegrationTest
import com.vntana.core.service.invitation.organization.dto.GetAllInvitationOrganizationsDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.stream.Collectors

/**
 * Created by Arman Gevorgyan.
 * Date: 3/26/20
 * Time: 4:55 PM
 */
class InvitationOrganizationGetAllServiceIntegrationTest : AbstractInvitationOrganizationServiceIntegrationTest() {

    @Test
    fun `test when empty`() {
        invitationOrganizationService.getAll(GetAllInvitationOrganizationsDto(10)).run {
            assertThat(totalElements).isEqualTo(0)
        }
    }

    @Test
    fun test() {
        val invitation1 = integrationTestHelper.persistInvitationOrganization()
        val invitation2 = integrationTestHelper.persistInvitationOrganization()
        flushAndClear()
        invitationOrganizationService.getAll(GetAllInvitationOrganizationsDto(10)).run {
            assertThat(totalElements).isEqualTo(2)
            val list = get().collect(Collectors.toList())
            assertThat(list).containsExactlyInAnyOrder(invitation1, invitation2)
        }
    }

}