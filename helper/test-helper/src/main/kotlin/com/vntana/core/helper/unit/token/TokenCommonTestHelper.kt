package com.vntana.core.helper.unit.token

import com.vntana.core.domain.invitation.organization.InvitationOrganization
import com.vntana.core.domain.invitation.user.InvitationUser
import com.vntana.core.domain.token.TokenInvitationOrganization
import com.vntana.core.domain.token.TokenInvitationUser
import com.vntana.core.domain.token.TokenResetPassword
import com.vntana.core.domain.user.User
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.invitation.organization.InvitationOrganizationCommonTestHelper
import com.vntana.core.helper.unit.invitation.user.InvitationUserCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.service.token.invitation.organization.dto.CreateTokenInvitationOrganizationDto
import com.vntana.core.service.token.invitation.user.dto.CreateTokenInvitationUserDto
import com.vntana.core.service.token.reset_password.dto.CreateTokenResetPasswordDto
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

    fun buildCreateTokenResetPasswordDto(
            token: String? = uuid(),
            userUuid: String? = uuid(),
            expirationDate: LocalDateTime? = LocalDateTime.now().plusDays(1)
    ): CreateTokenResetPasswordDto = CreateTokenResetPasswordDto(token, userUuid, expirationDate)

    fun buildTokenResetPassword(
            token: String? = uuid(),
            user: User? = userCommonTestHelper.buildUser()
    ): TokenResetPassword = TokenResetPassword(token, user)
}