package com.vntana.core.service.token.auth

import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.token.auth.AuthTokenIntegrationTestHelper
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arman Gevorgyan.
 * Date: 3/20/20
 * Time: 3:17 PM
 */
abstract class AbstractAuthTokenServiceIntegrationTest : AbstractServiceIntegrationTest() {

    @Autowired
    protected lateinit var authTokenService: AuthTokenService

    @Autowired
    protected lateinit var integrationTestHelper: AuthTokenIntegrationTestHelper

    @Autowired
    protected lateinit var userIntegrationTestHelper: UserIntegrationTestHelper

    @Autowired
    protected lateinit var organizationIntegrationTestHelper: OrganizationIntegrationTestHelper
}