package com.vntana.core.helper.unit.token.invitation.user

import com.vntana.core.domain.invitation.user.InvitationOrganizationUser
import com.vntana.core.domain.token.TokenUserInvitationToOrganization
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.invitation.user.InvitationUserCommonTestHelper
import com.vntana.core.service.token.invitation.user.dto.CreateInvitationUserToClientDto
import com.vntana.core.service.token.invitation.user.dto.CreateInvitationUserToOrganizationDto
import com.vntana.core.service.token.invitation.user.dto.InvitationUuidAndTokenDto

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 12:45 PM
 */
open class TokenInvitationUserCommonTestHelper : AbstractCommonTestHelper() {

    private val invitationUserCommonTestHelper = InvitationUserCommonTestHelper()

    fun buildCreateTokenInvitationUserToOrganizationDto(
            token: String? = uuid(),
            invitationUserUuid: String? = uuid()
    ): CreateInvitationUserToOrganizationDto = CreateInvitationUserToOrganizationDto(token, invitationUserUuid)
    
    fun buildCreateTokenForUserInvitationToClient(
            token: String? = uuid(),
            userInvitationUuid: String? = uuid()
    ):CreateInvitationUserToClientDto = CreateInvitationUserToClientDto(listOf(InvitationUuidAndTokenDto(userInvitationUuid, token)))

    fun buildTokenInvitationUserToOrganization(
            token: String? = uuid(),
            invitationOrganizationUser: InvitationOrganizationUser? = invitationUserCommonTestHelper.buildInvitationUserToOrganization()
    ): TokenUserInvitationToOrganization = TokenUserInvitationToOrganization(token, invitationOrganizationUser)
}