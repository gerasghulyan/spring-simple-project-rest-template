package com.vntana.core.helper.integration.token.invitation.organization

import com.vntana.core.domain.token.AuthToken
import com.vntana.core.domain.token.TokenInvitationOrganization
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.helper.unit.token.auth.AuthTokenCommonTestHelper
import com.vntana.core.helper.unit.token.invitation.organization.TokenInvitationOrganizationCommonTestHelper
import com.vntana.core.service.token.TokenService
import com.vntana.core.service.token.auth.AuthTokenService
import com.vntana.core.service.token.dto.CreateTokenInvitationOrganizationDto
import com.vntana.core.service.token.invitation.organization.TokenInvitationOrganizationService
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