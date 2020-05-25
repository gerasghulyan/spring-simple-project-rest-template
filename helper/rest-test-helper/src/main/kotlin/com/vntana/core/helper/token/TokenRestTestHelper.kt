package com.vntana.core.helper.token

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.token.request.CreateTokenInvitationOrganizationRequest
import com.vntana.core.model.token.request.CreateTokenInvitationUserRequest

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
    ): CreateTokenInvitationUserRequest = CreateTokenInvitationUserRequest(token, invitationUserUuid)
}