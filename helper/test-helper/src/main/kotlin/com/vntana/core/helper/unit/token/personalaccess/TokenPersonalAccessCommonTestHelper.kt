package com.vntana.core.helper.unit.token.personalaccess

import com.vntana.core.domain.token.TokenPersonalAccess
import com.vntana.core.domain.user.User
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.model.user.request.CreatePersonalAccessTokenRequest
import com.vntana.core.service.token.personalaccess.dto.CreatePersonalAccessTokenDto
import com.vntana.core.service.token.personalaccess.dto.RegeneratePersonalAccessTokenDto

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 12:45 AM
 */
open class TokenPersonalAccessCommonTestHelper : AbstractCommonTestHelper() {

    fun buildTokenPersonalAccessCreateDto(
        userUuid: String? = uuid(),
        token: String? = uuid()
    ): CreatePersonalAccessTokenDto = CreatePersonalAccessTokenDto(userUuid, token)

    fun buildTokenPersonalAccess(
        token: String? = uuid(),
        user: User? = null
    ): TokenPersonalAccess = TokenPersonalAccess(token, user)

    fun buildTokenPersonalAccessRegenerateDto(
        userUuid: String? = uuid(),
        token: String? = uuid()
    ): RegeneratePersonalAccessTokenDto = RegeneratePersonalAccessTokenDto(userUuid, token)

    fun buildCreatePersonalAccessTokenRequest(
        userUuid: String? = uuid(),
        token: String? = uuid()
    ): CreatePersonalAccessTokenRequest = CreatePersonalAccessTokenRequest(token, userUuid)
}