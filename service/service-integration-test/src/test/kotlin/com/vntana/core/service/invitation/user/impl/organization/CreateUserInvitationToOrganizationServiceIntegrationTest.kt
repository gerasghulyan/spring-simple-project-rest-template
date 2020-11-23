package com.vntana.core.service.invitation.user.impl.organization

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.service.invitation.user.AbstractInvitationUserToOrganizationServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:27 PM
 */
class CreateUserInvitationToOrganizationServiceIntegrationTest : AbstractInvitationUserToOrganizationServiceIntegrationTest() {

    @Test
    fun test() {
        val dto = integrationInvitationUserTestHelper.buildCreateInvitationUserForOrganizationDto(
                inviterUserUuid = userIntegrationTestHelper.persistUserWithOwnerRole().uuid,
                organizationUuid = organizationIntegrationTestHelper.persistOrganization().uuid
        )
        invitationUserToOrganizationService.create(dto).let {
            flushAndClear()
            assertThat(it.uuid).isNotBlank()
            assertThat(it.role).isEqualTo(dto.userRole)
            assertThat(it.email).isEqualTo(dto.email)
            assertThat(it.inviterUser.uuid).isEqualTo(dto.inviterUserUuid)
            assertThat(it.organization.uuid).isEqualTo(dto.organizationUuid)
            assertThat(it.status).isEqualTo(InvitationStatus.INVITED)
        }
    }
}