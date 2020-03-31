package com.vntana.core.helper.token.auth

import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.rest.client.token.auth.AuthTokenResourceClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 12:12 PM
 */
@Component
class AuthTokenResourceTestHelper : AuthTokenRestTestHelper() {

    @Autowired
    private lateinit var authTokenResourceClient: AuthTokenResourceClient

    @Autowired
    private lateinit var userResourceTestHelper: UserResourceTestHelper

    fun persistToken(userUuid: String = userResourceTestHelper.persistUser().response().uuid, token: String = uuid()): String {
        val request = buildAuthTokenPersistRequest(userUuid, token)
        authTokenResourceClient.persist(request)
        return token
    }
}