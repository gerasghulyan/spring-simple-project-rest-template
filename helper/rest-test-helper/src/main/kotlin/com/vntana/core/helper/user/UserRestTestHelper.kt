package com.vntana.core.helper.user

import com.vntana.core.helper.AbstractRestTestHelper
import com.vntana.core.model.user.request.*

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
            email: String? = email(),
            password: String? = uuid(),
            token: String? = uuid()
    ): CreateUserRequest = CreateUserRequest(clientName, clientSlug, fullName, email, password, token)

    fun buildFindUserByEmailRequest(email: String? = "${uuid()}@mail.com"): FindUserByEmailRequest = FindUserByEmailRequest(email)

    fun buildVerifyUserRequest(email: String? = uuid()): VerifyUserRequest = VerifyUserRequest(email)

    fun buildSendUserVerificationRequest(email: String? = uuid(), token: String? = uuid()
    ): SendUserVerificationRequest = SendUserVerificationRequest(email, token)

    fun buildSendUserResetPasswordRequest(email: String? = uuid(), token: String? = uuid()
    ): SendUserResetPasswordRequest = SendUserResetPasswordRequest(email, token)

    fun buildResetUserPasswordRequest(email: String? = uuid(), password: String? = uuid()
    ): ResetUserPasswordRequest = ResetUserPasswordRequest(email, password)

    fun email(): String = uuid() + "@gmail.com"
}