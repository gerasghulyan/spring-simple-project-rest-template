package com.vntana.core.helper.rest.user

import com.vntana.core.helper.rest.AbstractRestUnitTestHelper
import com.vntana.core.model.user.request.UserCreateRequest

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 11:27 AM
 */
class UserRestTestHelper : AbstractRestUnitTestHelper() {
    fun buildCreateUserRequest(
            clientName: String? = uuid(),
            clientSlug: String? = uuid(),
            fullName: String? = uuid(),
            email: String? = uuid(),
            password: String? = uuid()
    ): UserCreateRequest = UserCreateRequest(clientName, clientSlug, fullName, email, password)
}