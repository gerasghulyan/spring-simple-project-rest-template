package com.vntana.core.helper.user

import com.vntana.core.model.auth.response.UserRoleModel
import com.vntana.core.model.user.request.CreateUserRequest
import com.vntana.core.model.user.response.CreateUserResponse
import com.vntana.core.model.user.role.request.UserRoleGrantClientOrganizationRequest
import com.vntana.core.rest.client.user.UserResourceClient
import com.vntana.core.rest.client.user.role.UserRoleResourceClient
import org.springframework.beans.factory.annotation.Autowired
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