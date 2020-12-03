package com.vntana.core.service.token.reset.password

import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.helper.unit.token.TokenCommonTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import com.vntana.core.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Geras Ghulyan
 * Date: 6/23/20
 * Time: 2:40 PM
 */
abstract class AbstractTokenResetPasswordServiceIntegrationTest : AbstractServiceIntegrationTest() {

    @Autowired
    protected lateinit var tokenResetPasswordService: TokenResetPasswordService

    @Autowired
    protected lateinit var userService: UserService

    @Autowired
    protected lateinit var userIntegrationTestHelper: UserIntegrationTestHelper

    protected val commonTestHelper = TokenCommonTestHelper()

}