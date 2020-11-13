package com.vntana.core.helper.integration.token.auth

import com.vntana.core.domain.token.TokenAuthentication
import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.helper.unit.token.auth.TokenAuthenticationCommonTestHelper
import com.vntana.core.service.token.auth.TokenAuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 3:20 PM
 */
@Component
class TokenAuthenticationIntegrationTestHelper : TokenAuthenticationCommonTestHelper() {

    @Autowired
    private lateinit var tokenAuthenticationService: TokenAuthenticationService

    @Autowired
    private lateinit var userIntegrationTestHelper: UserIntegrationTestHelper

    @Autowired
    private lateinit var organizationIntegrationTestHelper: OrganizationIntegrationTestHelper
    
    fun persistAuthToken(token: String = uuid(), userUuid: String = userIntegrationTestHelper.persistUserWithOwnerRole().uuid): TokenAuthentication {
        val dto = buildTokenAuthenticationCreateDto(userUuid, token)
        return tokenAuthenticationService.create(dto)
    }

    fun persistAuthTokenWithOrganization(
            token: String = uuid(),
            userUuid: String = userIntegrationTestHelper.persistUserWithOwnerRole().uuid,
            organizationUuid: String = organizationIntegrationTestHelper.persistOrganization().uuid
    ): TokenAuthentication {
        val dto = buildTokenAuthenticationCreateWithOrganizationDto(userUuid, token, organizationUuid)
        return tokenAuthenticationService.createWithOrganization(dto)
    }
}