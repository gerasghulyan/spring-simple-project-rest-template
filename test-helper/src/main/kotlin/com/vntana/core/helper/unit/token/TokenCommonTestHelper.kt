package com.vntana.core.helper.unit.token

import com.vntana.core.domain.token.ResetPasswordToken
import com.vntana.core.domain.user.User
import com.vntana.core.helper.unit.AbstractCommonTestHelper
import com.vntana.core.helper.unit.user.UserCommonTestHelper
import com.vntana.core.service.token.dto.CreateResetPasswordTokenDto

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 2:33 PM
 */
open class TokenCommonTestHelper : AbstractCommonTestHelper() {

    private val userCommonTestHelper = UserCommonTestHelper()

    fun buildResetPasswordToken(
            token: String? = uuid(),
            user: User? = userCommonTestHelper.buildUser()
    ): ResetPasswordToken = ResetPasswordToken(token, user)

    fun buildCreateResetPasswordTokenDto(
            userUuid: String? = uuid()
    ): CreateResetPasswordTokenDto = CreateResetPasswordTokenDto(userUuid)
}