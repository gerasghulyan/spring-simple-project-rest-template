package com.vntana.core.helper.token

import com.vntana.core.helper.invitation.user.InvitationUserResourceTestHelper
import com.vntana.core.model.token.request.CreateTokenInvitationOrganizationRequest
import com.vntana.core.model.token.request.CreateTokenUserInvitationToClientRequest
import com.vntana.core.model.token.request.InvitationUuidAndTokenRequestModel
import com.vntana.core.model.token.response.TokenCreateResultResponse
import com.vntana.core.rest.client.token.TokenResourceClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

/**
 * Created by Arman Gevorgyan.
 * Date: 3/27/20
 * Time: 2:31 PM
 */
@Component
class TokenResourceTestHelper : TokenRestTestHelper() {

    @Autowired
    private lateinit var tokenResourceClient: TokenResourceClient

    @Autowired
    private lateinit var invitationUserResourceTestHelper: InvitationUserResourceTestHelper

    fun persistTokenInvitationOrganization(
            token: String = uuid(),
            invitationOrganizationUuid: String = uuid(),
            request: CreateTokenInvitationOrganizationRequest? = buildCreateTokenInvitationOrganizationRequest(token, invitationOrganizationUuid)
    ): ResponseEntity<TokenCreateResultResponse> = tokenResourceClient.createTokenInvitationOrganization(request)

    fun persistTokenInvitationUserToOrganization(
            token: String = uuid(),
            invitationUserUuid: String = invitationUserResourceTestHelper.persistInvitationUserToOrganization()
    ): String {
        val request = buildCreateTokenInvitationUserRequest(token = token, invitationUserUuid = invitationUserUuid)
        return tokenResourceClient.createTokenInvitationUserToOrganization(request)?.body?.response()?.uuid.toString()
    }

    fun persistTokenInvitationUserToClient(
            token: String = "Some token",
            invitationUserUuid: String = uuid()
    ): String {
        val request = CreateTokenUserInvitationToClientRequest(listOf(InvitationUuidAndTokenRequestModel(invitationUserUuid, token)))
        return tokenResourceClient.createTokenInvitationUserToClient(request)?.body?.response()?.uuids?.get(0).toString()
    }

    fun expire(token: String? = uuid()) {
        tokenResourceClient.expire(token)
    }
}