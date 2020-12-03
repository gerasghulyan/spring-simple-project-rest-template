package com.vntana.core.helper.unit.token

import com.vntana.core.domain.invitation.organization.InvitationOrganization
import com.vntana.core.domain.invitation.user.InvitationOrganizationClientUser
import com.vntana.core.domain.invitation.user.InvitationOrganizationUser
import com.vntana.core.domain.token.TokenInvitationOrganization
import com.vntana.core.domain.token.TokenResetPassword
import com.vntana.core.domain.token.TokenUserInvitationToOrganization
import com.vntana.core.domain.token.TokenUserInvitationToOrganizationClient
import com.vntana.core.domain.user.User
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.helper.unit.invitation.user.InvitationUserCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.service.token.invitation.organization.dto.CreateTokenInvitationOrganizationDto
import com.vntana.core.service.token.invitation.user.dto.CreateInvitationUserToOrganizationDto
import com.vntana.core.service.token.invitation.user.dto.InvitationUuidAndTokenDto
import com.vntana.core.service.token.reset.password.dto.CreateTokenResetPasswordDto
import java.time.LocalDateTime

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 2:33 PM
 */
open class TokenCommonTestHelper : AbstractCommonTestHelper() {

    private val invitationOrganizationCommonTestHelper = InvitationOrganizationCommonTestHelper()
    private val invitationUserCommonTestHelper = InvitationUserCommonTestHelper()
    private val userCommonTestHelper = UserCommonTestHelper()

    fun buildCreateTokenInvitationOrganizationDto(
            token: String? = uuid(),
            invitationOrganizationUuid: String? = uuid()
    ): CreateTokenInvitationOrganizationDto = CreateTokenInvitationOrganizationDto(token, invitationOrganizationUuid)

    fun buildCreateTokenInvitationUserToOrganizationDto(
            token: String? = uuid(),
            invitationUserUuid: String? = uuid()
    ): CreateInvitationUserToOrganizationDto = CreateInvitationUserToOrganizationDto(token, invitationUserUuid)

    fun buildInvitationUuidAndTokenDto(
            token: String? = uuid(),
            userInvitationUuid: String? = uuid()
    ): InvitationUuidAndTokenDto = InvitationUuidAndTokenDto(userInvitationUuid, token)

    fun buildTokenInvitationOrganization(
            token: String? = uuid(),
            invitationOrganization: InvitationOrganization? = invitationOrganizationCommonTestHelper.buildInvitationOrganization()
    ): TokenInvitationOrganization = TokenInvitationOrganization(token, invitationOrganization)

    fun buildTokenInvitationUserToOrganization(
            token: String? = uuid(),
            invitationOrganizationUser: InvitationOrganizationUser? = invitationUserCommonTestHelper.buildInvitationUserToOrganization()
    ): TokenUserInvitationToOrganization = TokenUserInvitationToOrganization(token, invitationOrganizationUser)

    fun buildTokenInvitationUserToClient(
            token: String? = uuid(),
            userInvitation: InvitationOrganizationClientUser? = invitationUserCommonTestHelper.buildInvitationOrganizationClientUser()
    ): TokenUserInvitationToOrganizationClient = TokenUserInvitationToOrganizationClient(token, userInvitation)

    fun buildCreateTokenResetPasswordDto(
            token: String? = uuid(),
            userUuid: String? = uuid(),
            expirationDate: LocalDateTime? = LocalDateTime.now().plusDays(1)
    ): CreateTokenResetPasswordDto = CreateTokenResetPasswordDto(token, userUuid, expirationDate)

    fun buildTokenResetPassword(
            token: String? = uuid(),
            expiration: LocalDateTime? = LocalDateTime.now().plusHours(1),
            user: User? = userCommonTestHelper.buildUser()
    ): TokenResetPassword = TokenResetPassword(token, expiration, user)
}