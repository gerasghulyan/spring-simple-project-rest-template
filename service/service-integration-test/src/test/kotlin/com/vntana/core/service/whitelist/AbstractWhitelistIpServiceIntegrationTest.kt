package com.vntana.core.service.whitelist

import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.whitelist.WhitelistIpIntegrationTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arman Gevorgyan.
 * Date: 11/28/19
 * Time: 1:53 PM
 */
abstract class AbstractWhitelistIpServiceIntegrationTest : AbstractServiceIntegrationTest() {

    @Autowired
    protected lateinit var whitelistIpService: WhitelistIpService

    @Autowired
    protected lateinit var testHelper: WhitelistIpIntegrationTestHelper

    @Autowired
    protected lateinit var organizationTestHelper: OrganizationIntegrationTestHelper
}