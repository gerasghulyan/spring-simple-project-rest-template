package com.vntana.core.helper.unit.token.invitation.user

import com.vntana.core.domain.invitation.user.InvitationUser
import com.vntana.core.domain.token.TokenInvitationUser
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.invitation.user.InvitationUserCommonTestHelper
import com.vntana.core.service.token.invitation.user.dto.CreateTokenInvitationUserDto

/**
 * Created by Arman Gevorgyan.
 * Date: 5/14/20
 * Time: 12:45 PM
 */
open class TokenInvitationUserCommonTestHelper : AbstractCommonTestHelper() {

    private val invitationUserCommonTestHelper = InvitationUserCommonTestHelper()

    fun buildCreateTokenInvitationUserDto(
            token: String? = uuid(),
            invitationUserUuid: String? = uuid()
    ): CreateTokenInvitationUserDto = CreateTokenInvitationUserDto(token, invitationUserUuid)

    fun buildTokenInvitationUser(
            token: String? = uuid(),
            invitationUser: InvitationUser? = invitationUserCommonTestHelper.buildInvitationUser()
    ): TokenInvitationUser = TokenInvitationUser(token, invitationUser)
}