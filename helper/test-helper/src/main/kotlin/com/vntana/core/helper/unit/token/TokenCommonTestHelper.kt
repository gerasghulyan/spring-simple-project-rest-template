package com.vntana.core.helper.unit.token

import com.vntana.core.domain.invitation.organization.InvitationOrganization
import com.vntana.core.domain.invitation.user.InvitationUser
import com.vntana.core.domain.token.TokenInvitationOrganization
import com.vntana.core.domain.token.TokenInvitationUser
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.helper.unit.invitation.user.InvitationUserCommonTestHelper
import com.vntana.core.service.token.invitation.organization.dto.CreateTokenInvitationOrganizationDto
import com.vntana.core.service.token.invitation.user.dto.CreateTokenInvitationUserDto

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 2:33 PM
 */
open class TokenCommonTestHelper : AbstractCommonTestHelper() {

    private val invitationOrganizationCommonTestHelper = InvitationOrganizationCommonTestHelper()
    private val invitationUserCommonTestHelper = InvitationUserCommonTestHelper()

    fun buildCreateTokenInvitationOrganizationDto(
            token: String? = uuid(),
            invitationOrganizationUuid: String? = uuid()
    ): CreateTokenInvitationOrganizationDto = CreateTokenInvitationOrganizationDto(token, invitationOrganizationUuid)

    fun buildCreateTokenInvitationUserDto(
            token: String? = uuid(),
            invitationUserUuid: String? = uuid()
    ): CreateTokenInvitationUserDto = CreateTokenInvitationUserDto(token, invitationUserUuid)
    
    fun buildTokenInvitationOrganization(
            token: String? = uuid(),
            invitationOrganization: InvitationOrganization? = invitationOrganizationCommonTestHelper.buildInvitationOrganization()
    ): TokenInvitationOrganization = TokenInvitationOrganization(token, invitationOrganization)

    fun buildTokenInvitationUser(
            token: String? = uuid(),
            invitationUser: InvitationUser? = invitationUserCommonTestHelper.buildInvitationUser()
    ): TokenInvitationUser = TokenInvitationUser(token, invitationUser)
}