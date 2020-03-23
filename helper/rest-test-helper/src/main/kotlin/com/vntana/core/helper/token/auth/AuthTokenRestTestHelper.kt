package com.vntana.core.helper.token.auth

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.token.auth.request.AuthTokenPersistRequest

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 11:44 AM
 */
class AuthTokenRestTestHelper : AbstractRestTestHelper() {

    fun buildAuthTokenPersistRequest(userUuid: String? = uuid(), token: String? = uuid()
    ): AuthTokenPersistRequest = AuthTokenPersistRequest(userUuid, token)
}