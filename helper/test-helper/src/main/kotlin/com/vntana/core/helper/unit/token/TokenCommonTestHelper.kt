package com.vntana.core.helper.unit.token

import com.vntana.core.domain.invitation.organization.InvitationOrganization
import com.vntana.core.domain.token.ResetPasswordToken
import com.vntana.core.domain.token.TokenInvitationOrganization
import com.vntana.core.domain.user.User
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.service.token.dto.CreateResetPasswordTokenDto
import com.vntana.core.service.token.dto.CreateTokenInvitationOrganizationDto

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 2:33 PM
 */
open class TokenCommonTestHelper : AbstractCommonTestHelper() {

    private val userCommonTestHelper = UserCommonTestHelper()
    private val invitationOrganizationCommonTestHelper = InvitationOrganizationCommonTestHelper()

    fun buildResetPasswordToken(
            token: String? = uuid(),
            user: User? = userCommonTestHelper.buildUser()
    ): ResetPasswordToken = ResetPasswordToken(token, user)

    fun buildCreateResetPasswordTokenDto(
            userUuid: String? = uuid()
    ): CreateResetPasswordTokenDto = CreateResetPasswordTokenDto(userUuid)

    fun buildCreateTokenInvitationOrganizationDto(
            token: String? = uuid(),
            invitationOrganizationUuid: String? = uuid()
    ): CreateTokenInvitationOrganizationDto = CreateTokenInvitationOrganizationDto(token, invitationOrganizationUuid)

    fun buildTokenInvitationOrganization(
            token: String? = uuid(),
            invitationOrganization: InvitationOrganization? = invitationOrganizationCommonTestHelper.buildInvitationOrganization()
    ): TokenInvitationOrganization = TokenInvitationOrganization(token, invitationOrganization)
}