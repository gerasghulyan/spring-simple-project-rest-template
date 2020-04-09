package com.vntana.core.helper.integration.token

import com.vntana.core.domain.token.TokenInvitationOrganization
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.helper.unit.token.TokenCommonTestHelper
import com.vntana.core.service.token.TokenService
import com.vntana.core.service.token.dto.CreateTokenInvitationOrganizationDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 3:00 PM
 */
@Component
class TokenIntegrationTestHelper : TokenCommonTestHelper() {

    @Autowired
    private lateinit var userIntegrationTestHelper: UserIntegrationTestHelper

    @Autowired
    private lateinit var tokenService: TokenService

    fun persistTokenInvitationOrganization(
            token: String? = uuid(),
            invitationOrganizationUuid: String? = uuid(),
            dto: CreateTokenInvitationOrganizationDto? = buildCreateTokenInvitationOrganizationDto(token, invitationOrganizationUuid)
    ): TokenInvitationOrganization = tokenService.createTokenInvitationOrganization(dto)
}