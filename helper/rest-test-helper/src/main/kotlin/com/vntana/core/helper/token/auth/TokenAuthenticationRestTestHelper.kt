package com.vntana.core.helper.token.auth

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.token.auth.request.TokenAuthenticationPersistRequest
import com.vntana.core.model.token.auth.request.TokenAuthenticationPersistWithClientOrganizationRequest
import com.vntana.core.model.token.auth.request.TokenAuthenticationPersistWithOrganizationRequest
import com.vntana.core.model.token.auth.request.TokenAuthenticationRequest
import java.time.LocalDateTime

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 11:44 AM
 */
open class TokenAuthenticationRestTestHelper : AbstractRestTestHelper() {

    fun buildTokenAuthenticationPersistRequest(
            userUuid: String? = uuid(),
            token: String? = uuid(),
            expiration: LocalDateTime? = LocalDateTime.now().plusDays(10)
    ): TokenAuthenticationPersistRequest = TokenAuthenticationPersistRequest(userUuid, token, expiration)

    fun buildTokenAuthenticationPersistWithOrganizationRequest(
            userUuid: String? = uuid(),
            token: String? = uuid(),
            organizationUuid: String? = uuid(),
            expiration: LocalDateTime? = LocalDateTime.now().plusDays(10)
    ): TokenAuthenticationPersistWithOrganizationRequest = TokenAuthenticationPersistWithOrganizationRequest(userUuid, token, expiration, organizationUuid)

    fun buildTokenAuthenticationPersistWithClientOrganizationRequest(
            userUuid: String? = uuid(),
            token: String? = uuid(),
            clientUuid: String? = uuid(),
            expiration: LocalDateTime? = LocalDateTime.now().plusDays(10)
    ): TokenAuthenticationPersistWithClientOrganizationRequest = TokenAuthenticationPersistWithClientOrganizationRequest(userUuid, token, expiration, clientUuid)

    fun buildTokenAuthenticationRequest(
            token: String? = uuid()
    ): TokenAuthenticationRequest = TokenAuthenticationRequest(token)
}