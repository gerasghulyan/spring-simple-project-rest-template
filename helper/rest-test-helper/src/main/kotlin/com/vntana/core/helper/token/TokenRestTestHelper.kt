package com.vntana.core.helper.token

import com.vntana.core.helper.AbstractRestTestHelper
import com.vntana.core.model.token.request.ResetPasswordRequest

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 3:32 PM
 */
open class TokenRestTestHelper : AbstractRestTestHelper() {
    fun buildResetPasswordRequest(email: String? = uuid()): ResetPasswordRequest = ResetPasswordRequest(email)
}