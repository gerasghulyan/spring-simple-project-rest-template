package com.vntana.core.rest.resource.user

import com.vntana.core.models.user.response.UserCreateRequest
import com.vntana.core.rest.resource.AbstractWebHelper
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 11:27 AM
 */
@Component
class UserWebHelper : AbstractWebHelper() {

    fun buildCreateUserRequest(firstName: String? = uuid(), secondName: String? = uuid()):
            UserCreateRequest = UserCreateRequest(firstName, secondName)

}