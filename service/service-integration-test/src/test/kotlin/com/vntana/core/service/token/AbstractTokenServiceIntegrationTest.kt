package com.vntana.core.service.token

import com.vntana.core.helper.integration.token.TokenIntegrationTestHelper
import com.vntana.core.helper.unit.token.TokenCommonTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 10/21/19
 * Time: 2:56 PM
 */
abstract class AbstractTokenServiceIntegrationTest : AbstractServiceIntegrationTest() {
    @Autowired
    protected lateinit var tokenService: TokenService

    @Autowired
    protected lateinit var resetPasswordTokenService: ResetPasswordTokenService

    @Autowired
    protected lateinit var integrationTestHelper: TokenIntegrationTestHelper

    protected val unitTestHelper = TokenCommonTestHelper()
}