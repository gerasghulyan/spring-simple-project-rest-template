package com.vntana.core.service.user.external

import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Diana Gevorgyan
 * Date: 7/13/2021
 * Time: 3:59 PM
 */
abstract class AbstractExternalUserServiceIntegrationTest : AbstractServiceIntegrationTest() {

    @Autowired
    protected lateinit var integrationTestHelper: UserIntegrationTestHelper

    @Autowired
    protected lateinit var externalUserService: ExternalUserService
    
    @Autowired
    protected lateinit var organizationIntegrationTestHelper: OrganizationIntegrationTestHelper
}