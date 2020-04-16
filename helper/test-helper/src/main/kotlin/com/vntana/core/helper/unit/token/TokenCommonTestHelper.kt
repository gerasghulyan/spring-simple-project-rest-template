package com.vntana.core.helper.unit.token

import com.vntana.core.domain.invitation.organization.InvitationOrganization
import com.vntana.core.domain.token.TokenInvitationOrganization
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.service.token.dto.CreateTokenInvitationOrganizationDto

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 2:33 PM
 */
open class TokenCommonTestHelper : AbstractCommonTestHelper() {

    private val invitationOrganizationCommonTestHelper = InvitationOrganizationCommonTestHelper()
    
    fun buildCreateTokenInvitationOrganizationDto(
            token: String? = uuid(),
            invitationOrganizationUuid: String? = uuid()
    ): CreateTokenInvitationOrganizationDto = CreateTokenInvitationOrganizationDto(token, invitationOrganizationUuid)

    fun buildTokenInvitationOrganization(
            token: String? = uuid(),
            invitationOrganization: InvitationOrganization? = invitationOrganizationCommonTestHelper.buildInvitationOrganization()
    ): TokenInvitationOrganization = TokenInvitationOrganization(token, invitationOrganization)
}