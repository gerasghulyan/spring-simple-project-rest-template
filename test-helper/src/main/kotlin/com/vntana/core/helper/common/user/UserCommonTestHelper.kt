package com.vntana.core.helper.common.user

import com.vntana.core.helper.common.AbstractCommonTestHelper
import com.vntana.core.service.user.dto.UserCreateDto

/**
 * Created by Arthur Asatryan.
 * Date: 10/1/19
 * Time: 3:46 PM
 */
open class UserCommonTestHelper : AbstractCommonTestHelper() {

    fun buildUserCreateDto(firstName: String? = uuid(), secondName: String? = uuid()):
            UserCreateDto = UserCreateDto(firstName, secondName)
}