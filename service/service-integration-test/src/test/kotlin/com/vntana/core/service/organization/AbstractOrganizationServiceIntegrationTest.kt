package com.vntana.core.service.organization

import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.user.UserServiceIntegrationTestHelper
import com.vntana.core.helper.unit.organization.OrganizationCommonTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 10:46 AM
 */
abstract class AbstractOrganizationServiceIntegrationTest : AbstractServiceIntegrationTest() {
    @Autowired
    protected lateinit var organizationService: OrganizationService

    @Autowired
    protected lateinit var integrationTestHelper: OrganizationIntegrationTestHelper

    @Autowired
    protected lateinit var userIntegrationTestHelper: UserServiceIntegrationTestHelper

    protected val commonTestHelper: OrganizationCommonTestHelper = OrganizationCommonTestHelper()
}