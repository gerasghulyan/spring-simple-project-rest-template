package com.vntana.core.helper.integration.token.auth

import com.vntana.core.domain.token.AuthToken
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.helper.unit.token.auth.AuthTokenCommonTestHelper
import com.vntana.core.service.token.auth.AuthTokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 3:20 PM
 */
@Component
class AuthTokenIntegrationTestHelper : AuthTokenCommonTestHelper() {

    @Autowired
    private lateinit var authTokenService: AuthTokenService

    @Autowired
    private lateinit var userIntegrationTestHelper: UserIntegrationTestHelper

    fun persistAuthToken(token: String = uuid(), userUuid: String = userIntegrationTestHelper.persistUserWithOwnerRole().uuid): AuthToken {
        val dto = buildAuthTokenCreateDto(userUuid, token)
        return authTokenService.create(dto)
    }
}