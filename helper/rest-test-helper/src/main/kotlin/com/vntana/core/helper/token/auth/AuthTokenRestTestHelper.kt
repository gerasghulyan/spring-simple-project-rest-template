package com.vntana.core.helper.token.auth

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.token.auth.request.AuthTokenPersistRequest
import com.vntana.core.model.token.auth.request.AuthTokenPersistWithOrganizationRequest
import com.vntana.core.model.token.auth.request.AuthTokenRequest
import java.time.LocalDateTime

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 11:44 AM
 */
open class AuthTokenRestTestHelper : AbstractRestTestHelper() {

    fun buildAuthTokenPersistRequest(
            userUuid: String? = uuid(),
            token: String? = uuid(),
            expiration: LocalDateTime? = LocalDateTime.now().plusDays(10)
    ): AuthTokenPersistRequest = AuthTokenPersistRequest(userUuid, token, expiration)

    fun buildAuthTokenPersistWithOrganizationRequest(
            userUuid: String? = uuid(),
            token: String? = uuid(),
            organizationUuid: String? = uuid(),
            expiration: LocalDateTime? = LocalDateTime.now().plusDays(10)
    ): AuthTokenPersistWithOrganizationRequest = AuthTokenPersistWithOrganizationRequest(userUuid, token, organizationUuid, expiration)

    fun buildAuthTokenRequest(
            token: String? = uuid()
    ): AuthTokenRequest = AuthTokenRequest(token)
}