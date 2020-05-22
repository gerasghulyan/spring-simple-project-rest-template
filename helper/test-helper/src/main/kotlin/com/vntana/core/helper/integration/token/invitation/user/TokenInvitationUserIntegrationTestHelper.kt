package com.vntana.core.helper.integration.token.invitation.user

import com.vntana.core.domain.token.TokenInvitationUser
import com.vntana.core.helper.integration.invitation.user.InvitationUserIntegrationTestHelper
import com.vntana.core.helper.unit.token.invitation.user.TokenInvitationUserCommonTestHelper
import com.vntana.core.service.token.invitation.user.TokenInvitationUserService
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

    fun persistTokenInvitationUser(
            token: String? = uuid(),
            invitationUserUuid: String? = invitationUserIntegrationTestHelper.persistInvitationUser().uuid
    ): TokenInvitationUser {
        val dto = buildCreateTokenInvitationUserDto(token, invitationUserUuid)
        return tokenInvitationUserService.create(dto);
    }
}