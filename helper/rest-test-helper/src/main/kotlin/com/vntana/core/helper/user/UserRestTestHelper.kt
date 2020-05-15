package com.vntana.core.helper.user

import com.vntana.commons.helper.AbstractRestTestHelper
import com.vntana.core.model.security.request.FindUserByUuidAndOrganizationRequest
import com.vntana.core.model.user.request.*

/**
 * Created by Arthur Asatryan.
 * Date: 10/2/19
 * Time: 11:27 AM
 */
open class UserRestTestHelper : AbstractRestTestHelper() {
    fun buildCreateUserRequest(
            organizationName: String? = uuid(),
            organizationSlug: String? = uuid(),
            fullName: String? = uuid(),
            email: String? = email(),
            password: String? = uuid()
    ): CreateUserRequest = CreateUserRequest(organizationName, organizationSlug, fullName, email, password)

    fun buildFindUserByEmailRequest(email: String? = "${uuid()}@mail.com"): FindUserByEmailRequest = FindUserByEmailRequest(email)

    fun buildFindUserByUuidAndOrganizationRequest(uuid: String? = uuid(),
                                                  organizationUuid: String? = uuid()
    ): FindUserByUuidAndOrganizationRequest = FindUserByUuidAndOrganizationRequest(uuid, organizationUuid)

    fun buildVerifyUserRequest(email: String? = uuid()): VerifyUserRequest = VerifyUserRequest(email)

    fun buildSendUserVerificationRequest(email: String? = uuid(), token: String? = uuid()
    ): SendUserVerificationRequest = SendUserVerificationRequest(email, token)

    fun buildSendUserResetPasswordRequest(email: String? = uuid(), token: String? = uuid()
    ): SendUserResetPasswordRequest = SendUserResetPasswordRequest(email, token)

    fun buildResetUserPasswordRequest(email: String? = uuid(), password: String? = uuid()
    ): ResetUserPasswordRequest = ResetUserPasswordRequest(email, password)

    fun email(): String = uuid() + "@gmail.com"

    fun buildUpdateUserRequest(
            uuid: String? = uuid(),
            imageBlobId: String? = uuid(),
            fullName: String? = uuid()
    ): UpdateUserRequest {
        return UpdateUserRequest(uuid, imageBlobId, fullName)
    }

    fun buildChangeUserPasswordRequest(
            uuid: String? = uuid(),
            oldPassword: String? = uuid(),
            newPassword: String? = uuid()
    ): ChangeUserPasswordRequest {
        return ChangeUserPasswordRequest(uuid, oldPassword, newPassword)
    }
}