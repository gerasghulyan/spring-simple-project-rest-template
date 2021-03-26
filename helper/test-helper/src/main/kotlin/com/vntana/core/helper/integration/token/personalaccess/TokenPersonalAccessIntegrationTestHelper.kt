package com.vntana.core.helper.integration.token.personalaccess

import com.vntana.core.domain.token.TokenPersonalAccess
import com.vntana.core.helper.unit.token.personalaccess.TokenPersonalAccessCommonTestHelper
import com.vntana.core.service.token.personalaccess.PersonalAccessTokenService
import com.vntana.core.service.token.personalaccess.dto.CreatePersonalAccessTokenDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 1:04 AM
 */
@Component
class TokenPersonalAccessIntegrationTestHelper : TokenPersonalAccessCommonTestHelper() {

    @Autowired
    private lateinit var personalAccessTokenService: PersonalAccessTokenService

    fun persistPersonalAccessToken(
        token: String? = uuid(),
        userUuid: String? = null,
        dto: CreatePersonalAccessTokenDto? = buildTokenPersonalAccessCreateDto(userUuid, token)
    ): TokenPersonalAccess = personalAccessTokenService.create(dto)

    fun expirePersonalAccessToken(
        tokenUuid: String? = uuid()
    ): TokenPersonalAccess = personalAccessTokenService.expire(tokenUuid)
}