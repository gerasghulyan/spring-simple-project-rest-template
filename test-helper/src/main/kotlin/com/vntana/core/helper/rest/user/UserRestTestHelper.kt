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
            firstName: String? = uuid(),
            secondName: String? = uuid()
    ): UserCreateRequest = UserCreateRequest(firstName, secondName)
}