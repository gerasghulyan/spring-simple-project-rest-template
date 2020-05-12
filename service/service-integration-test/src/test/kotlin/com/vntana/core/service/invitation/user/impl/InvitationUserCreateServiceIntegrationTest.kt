package com.vntana.core.service.invitation.user.impl

import com.vntana.core.domain.invitation.InvitationStatus
import com.vntana.core.service.invitation.user.AbstractInvitationUserServiceIntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

/**
 * Created by Manuk Gharslyan.
 * Date: 5/11/2020
 * Time: 1:27 PM
 */
class InvitationUserCreateServiceIntegrationTest : AbstractInvitationUserServiceIntegrationTest() {

    @Test
    fun test() {
        val dto = integrationUserTestHelper.buildCreateInvitationUserDto(
                inviterUserUuid = userIntegrationTestHelper.persistUser().uuid,
                organizationUuid = organizationIntegrationTestHelper.persistOrganization().uuid
        )
        invitationUserService.create(dto).let {
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