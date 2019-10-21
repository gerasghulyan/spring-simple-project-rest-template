package com.vntana.core.helper.rest.token

import com.vntana.core.helper.rest.AbstractRestUnitTestHelper
import com.vntana.core.model.token.request.ResetPasswordRequest

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 3:32 PM
 */
class TokenRestTestHelper : AbstractRestUnitTestHelper() {
    fun buildResetPasswordRequest(email: String? = uuid()): ResetPasswordRequest = ResetPasswordRequest(email)
}