package com.vntana.core.service.user.role

import com.vntana.core.helper.integration.client.ClientOrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arman Gevorgyan.
 * Date: 5/7/20
 * Time: 2:11 PM
 */
abstract class AbstractUserRoleServiceIntegrationTest : AbstractServiceIntegrationTest() {

    @Autowired
    protected lateinit var userRoleService: UserRoleService

    @Autowired
    protected lateinit var integrationTestHelper: UserIntegrationTestHelper

    @Autowired
    protected lateinit var organizationIntegrationTest: OrganizationIntegrationTestHelper

    @Autowired
    protected lateinit var clientOrganizationIntegrationTest: ClientOrganizationIntegrationTestHelper
}