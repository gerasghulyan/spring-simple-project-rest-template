package com.vntana.core.helper.token

import com.vntana.core.model.token.request.CreateTokenInvitationOrganizationRequest
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

    fun persistTokenInvitationOrganization(
            token: String = uuid(),
            invitationOrganizationUuid: String = uuid(),
            request: CreateTokenInvitationOrganizationRequest? = buildCreateTokenInvitationOrganizationRequest(token, invitationOrganizationUuid)
    ): ResponseEntity<TokenCreateResultResponse> = tokenResourceClient.createTokenInvitationOrganization(request)

    fun expire(token: String? = uuid()) {
        tokenResourceClient.expire(token)
    }
}