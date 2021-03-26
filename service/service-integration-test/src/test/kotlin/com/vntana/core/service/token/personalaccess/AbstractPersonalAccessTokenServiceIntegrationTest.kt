package com.vntana.core.service.token.personalaccess

import com.vntana.core.helper.integration.token.personalaccess.TokenPersonalAccessIntegrationTestHelper
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Diana Gevorgyan
 * Date: 3/25/21
 * Time: 12:56 AM
 */
abstract class AbstractPersonalAccessTokenServiceIntegrationTest : AbstractServiceIntegrationTest() {

    @Autowired
    protected lateinit var personalAccessTokenService: PersonalAccessTokenService

    @Autowired
    protected lateinit var userIntegrationTestHelper: UserIntegrationTestHelper

    @Autowired
    protected lateinit var integrationTestHelper: TokenPersonalAccessIntegrationTestHelper
}