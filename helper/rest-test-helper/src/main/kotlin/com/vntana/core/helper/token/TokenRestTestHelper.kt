package com.vntana.core.helper.token

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.token.request.CreateTokenInvitationOrganizationRequest
import com.vntana.core.model.token.request.CreateTokenInvitationUserToOrganizationRequest
import com.vntana.core.model.token.request.CreateTokenUserInvitationToClientRequest
import com.vntana.core.model.token.request.InvitationAndTokenRequestModel

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 2:03 PM
 */
open class TokenRestTestHelper : AbstractRestTestHelper() {

    fun buildCreateTokenInvitationOrganizationRequest(
            token: String? = uuid(),
            invitationOrganizationUuid: String? = uuid()
    ): CreateTokenInvitationOrganizationRequest = CreateTokenInvitationOrganizationRequest(token, invitationOrganizationUuid)

    fun buildCreateTokenInvitationUserRequest(
            token: String? = uuid(),
            invitationUserUuid: String? = uuid()
    ): CreateTokenInvitationUserToOrganizationRequest = CreateTokenInvitationUserToOrganizationRequest(token, invitationUserUuid)
    
    fun buildCreateTokenUserInvitationToClientRequest(
            size: Int? = 1
    ): CreateTokenUserInvitationToClientRequest = CreateTokenUserInvitationToClientRequest(size?.let { List(it) { buildCreateInvitationUuidAndToken() } })

    private fun buildCreateInvitationUuidAndToken(
            token: String? = uuid(),
            userInvitationUuid: String? = uuid()
    ): InvitationAndTokenRequestModel = InvitationAndTokenRequestModel(userInvitationUuid, token)
}