package com.vntana.core.service.token.invitation.user.impl

import com.vntana.core.service.token.invitation.user.AbstractTokenInvitationUserServiceIntegrationTest
import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 2:03 PM
 */
class TokenInvitationUserToOrganizationCreateServiceIntegrationTest : AbstractTokenInvitationUserServiceIntegrationTest() {

    @Test
    fun test() {
        val invitationUser = invitationUserIntegrationTestHelper.persistInvitationUserToOrganization()
        val dto = integrationTestHelper.buildCreateTokenInvitationUserToOrganizationDto(
                invitationUserUuid = invitationUser.uuid
        )
        flushAndClear()
        tokenInvitationUserService.createUserInvitationToOrganization(dto).let {
            flushAndClear()
            Assertions.assertThat(it.token).isEqualTo(dto.token)
            Assertions.assertThat(it.invitationUser).isEqualTo(invitationUser)
        }
    }
}