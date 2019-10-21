package com.vntana.core.rest.resource.token

import com.vntana.core.helper.rest.token.TokenRestTestHelper
import com.vntana.core.rest.client.token.ResetPasswordTokenResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 3:51 PM
 */
abstract class AbstractTokenWebTest : AbstractWebIntegrationTest() {
    protected val restTestHelper = TokenRestTestHelper()

    @Autowired
    protected lateinit var resetPasswordTokenResourceClient: ResetPasswordTokenResourceClient
}