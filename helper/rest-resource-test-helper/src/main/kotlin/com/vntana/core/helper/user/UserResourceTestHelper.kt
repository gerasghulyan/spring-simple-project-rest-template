package com.vntana.core.helper.user

import com.vntana.core.model.user.request.CreatePersonalAccessTokenRequest
import com.vntana.core.model.user.request.CreateUserRequest
import com.vntana.core.model.user.response.CreateUserResponse
import com.vntana.core.model.user.response.personalaccess.PersonalAccessTokenResponse
import com.vntana.core.rest.client.user.UserResourceClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

/**
 * Created by Arman Gevorgyan.
 * Date: 10/23/19
 * Time: 5:06 PM
 */
@Component
class UserResourceTestHelper : UserRestTestHelper() {

    @Autowired
    lateinit var userResourceClient: UserResourceClient

    fun persistUser(createUserRequest: CreateUserRequest = buildCreateUserRequest()): CreateUserResponse {
        return userResourceClient.createUser(createUserRequest)
    }

    fun persistVerifiedUser(request: CreateUserRequest = buildCreateUserRequest()) {
        persistUser(request)
        userResourceClient.verify(buildVerifyUserRequest(email = request.email))
    }

    fun persistPersonalAccessToken(request: CreatePersonalAccessTokenRequest): ResponseEntity<PersonalAccessTokenResponse> {
        return userResourceClient.createPersonalAccessToken(request)
    }

    fun buildUserInvalidEmail(): String = uuid()

    fun persistTokenResetPassword(
        email: String? = uuid(),
        token: String? = uuid()
    ): String? {
        val request = buildSendUserResetPasswordRequest(email, token)
        userResourceClient.sendResetPassword(request)
        return token
    }
}