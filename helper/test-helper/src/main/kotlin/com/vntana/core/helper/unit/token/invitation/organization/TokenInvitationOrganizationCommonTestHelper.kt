package com.vntana.core.helper.unit.token.invitation.organization

import com.vntana.core.domain.invitation.organization.InvitationOrganization
import com.vntana.core.domain.token.TokenInvitationOrganization
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.service.token.invitation.organization.dto.CreateTokenInvitationOrganizationDto

/**
 * Created by Geras Ghulyan
 * Date: 3/20/20
 * Time: 3:20 PM
 */
open class TokenInvitationOrganizationCommonTestHelper : AbstractCommonTestHelper() {

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