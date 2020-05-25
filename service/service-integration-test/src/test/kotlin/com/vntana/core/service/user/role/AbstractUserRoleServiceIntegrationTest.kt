package com.vntana.core.service.user.role

import com.vntana.core.helper.integration.client.ClientOrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.user.UserIntegrationTestHelper
import com.vntana.core.helper.integration.user.role.UserRoleIntegrationTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import com.vntana.core.service.user.UserService
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
    protected lateinit var userService: UserService

    @Autowired
    protected lateinit var integrationTestHelper: UserRoleIntegrationTestHelper

    @Autowired
    protected lateinit var userIntegrationTestHelper: UserIntegrationTestHelper

    @Autowired
    protected lateinit var organizationIntegrationTestHelper: OrganizationIntegrationTestHelper

    @Autowired
    protected lateinit var clientOrganizationIntegrationTestHelper: ClientOrganizationIntegrationTestHelper
}