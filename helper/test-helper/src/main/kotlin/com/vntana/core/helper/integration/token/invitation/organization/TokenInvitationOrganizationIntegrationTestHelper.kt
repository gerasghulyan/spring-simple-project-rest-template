package com.vntana.core.helper.integration.token.invitation.organization

import com.vntana.core.domain.token.TokenInvitationOrganization
import com.vntana.core.helper.unit.token.invitation.organization.TokenInvitationOrganizationCommonTestHelper
import com.vntana.core.service.token.invitation.organization.TokenInvitationOrganizationService
import com.vntana.core.service.token.invitation.organization.dto.CreateTokenInvitationOrganizationDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Geras Ghulyan
 * Date: 3/20/20
 * Time: 3:20 PM
 */
@Component
class TokenInvitationOrganizationIntegrationTestHelper : TokenInvitationOrganizationCommonTestHelper() {

    @Autowired
    private lateinit var tokenInvitationOrganizationService: TokenInvitationOrganizationService

    fun persistTokenInvitationOrganization(
            token: String? = uuid(),
            invitationOrganizationUuid: String? = uuid(),
            dto: CreateTokenInvitationOrganizationDto? = buildCreateTokenInvitationOrganizationDto(token, invitationOrganizationUuid)
    ): TokenInvitationOrganization = tokenInvitationOrganizationService.create(dto)
}