package com.vntana.core.helper.integration.token.invitation.user

import com.vntana.core.domain.token.TokenUserInvitationToOrganization
import com.vntana.core.domain.token.TokenUserInvitationToOrganizationClient
import com.vntana.core.helper.integration.invitation.user.InvitationUserIntegrationTestHelper
import com.vntana.core.helper.unit.token.invitation.user.TokenInvitationUserCommonTestHelper
import com.vntana.core.service.token.invitation.user.TokenInvitationUserService
import com.vntana.core.service.token.invitation.user.dto.CreateInvitationUserToClientDto
import com.vntana.core.service.token.invitation.user.dto.InvitationUuidAndTokenDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 1:01 PM
 */
@Component
class TokenInvitationUserIntegrationTestHelper : TokenInvitationUserCommonTestHelper() {

    @Autowired
    private lateinit var tokenInvitationUserService: TokenInvitationUserService

    @Autowired
    private lateinit var invitationUserIntegrationTestHelper: InvitationUserIntegrationTestHelper

    fun persistTokenInvitationUserToOrganization(
            token: String? = uuid(),
            invitationUserUuid: String? = invitationUserIntegrationTestHelper.persistInvitationUserToOrganization().uuid
    ): TokenUserInvitationToOrganization {
        val dto = buildCreateTokenInvitationUserToOrganizationDto(token, invitationUserUuid)
        return tokenInvitationUserService.createUserInvitationToOrganization(dto);
    }

    fun persistTokenInvitationUserToClient(
            token: String? = uuid(),
            invitationUserUuid: String? = invitationUserIntegrationTestHelper.persistUserInvitationToClient().uuid
    ): TokenUserInvitationToOrganizationClient {
        val dto = CreateInvitationUserToClientDto(listOf(InvitationUuidAndTokenDto(invitationUserUuid, token)))
        return tokenInvitationUserService.createUserInvitationToClients(dto)[0];
    }
}