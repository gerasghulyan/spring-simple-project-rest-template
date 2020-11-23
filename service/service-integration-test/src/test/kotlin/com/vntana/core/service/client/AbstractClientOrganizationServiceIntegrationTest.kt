package com.vntana.core.service.client

import com.vntana.core.helper.integration.client.ClientOrganizationIntegrationTestHelper
import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.unit.client.ClientOrganizationCommonTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 10:46 AM
 */
abstract class AbstractClientOrganizationServiceIntegrationTest : AbstractServiceIntegrationTest() {
    @Autowired
    protected lateinit var organizationClientService: OrganizationClientService

    @Autowired
    protected lateinit var integrationTestHelper: ClientOrganizationIntegrationTestHelper

    @Autowired
    protected lateinit var organizationIntegrationTestHelper: OrganizationIntegrationTestHelper

    protected val commonTestHelper: ClientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()
}