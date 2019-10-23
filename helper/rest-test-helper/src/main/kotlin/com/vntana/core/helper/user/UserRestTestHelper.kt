package com.vntana.core.helper.user

import com.vntana.core.helper.AbstractRestTestHelper
import com.vntana.core.model.user.request.CreateUserRequest
import com.vntana.core.model.user.request.FindUserByEmailRequest

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 11:27 AM
 */
open class UserRestTestHelper : AbstractRestTestHelper() {
    fun buildCreateUserRequest(
            clientName: String? = uuid(),
            clientSlug: String? = uuid(),
            fullName: String? = uuid(),
            email: String? = uuid(),
            password: String? = uuid()
    ): CreateUserRequest = CreateUserRequest(clientName, clientSlug, fullName, email, password)

    fun buildFindUserByEmailRequest(email: String? = uuid()): FindUserByEmailRequest = FindUserByEmailRequest(email)
}