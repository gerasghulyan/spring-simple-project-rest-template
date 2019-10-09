package com.vntana.core.helper.rest

import com.vntana.core.helper.common.AbstractCommonTestHelper
import com.vntana.core.model.user.request.UserCreateRequest

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 11:27 AM
 */
class UserRestTestHelper : AbstractCommonTestHelper() {
    fun buildCreateUserRequest(
            firstName: String? = uuid(),
            secondName: String? = uuid()
    ): UserCreateRequest = UserCreateRequest(firstName, secondName)
}