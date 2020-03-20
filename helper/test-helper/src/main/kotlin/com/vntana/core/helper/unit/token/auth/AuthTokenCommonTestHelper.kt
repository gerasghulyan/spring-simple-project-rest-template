package com.vntana.core.helper.unit.token.auth

import com.vntana.core.domain.token.AuthToken
import com.vntana.core.domain.user.User
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.service.token.auth.dto.AuthTokenCreateDto

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 3:20 PM
 */
open class AuthTokenCommonTestHelper : AbstractCommonTestHelper() {

    private val userCommonTestHelper = UserCommonTestHelper()

    fun buildAuthTokenCreateDto(userUuid: String? = uuid(), token: String? = uuid()
    ): AuthTokenCreateDto = AuthTokenCreateDto(userUuid, token)

    fun buildAuthToken(token: String = uuid(), user: User = userCommonTestHelper.buildUser()
    ): AuthToken = AuthToken(token, user)
}