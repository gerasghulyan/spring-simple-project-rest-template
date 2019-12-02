package com.vntana.core.rest.resource.whitelist

import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.helper.whitelist.WhitelistIpResourceTestHelper
import com.vntana.core.rest.client.whitelist.WhitelistIpResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arman Gevorgyan.
 * Date: 11/29/19
 * Time: 12:37 PM
 */
abstract class AbstractWhitelistIpWebTest : AbstractWebIntegrationTest() {

    @Autowired
    protected lateinit var whitelistIpResourceClient: WhitelistIpResourceClient

    @Autowired
    protected lateinit var testHelper: WhitelistIpResourceTestHelper

    @Autowired
    protected lateinit var organizationTestHelper: OrganizationResourceTestHelper
}