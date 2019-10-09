package com.vntana.core.service.client

import com.vntana.core.helper.common.client.ClientOrganizationCommonTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 10:46 AM
 */
abstract class AbstractClientOrganizationServiceIntegrationTest : AbstractServiceIntegrationTest() {

    @Autowired
    protected lateinit var clientOrganizationService: ClientOrganizationService

    @Autowired
    protected lateinit var integrationTestHelper: ClientOrganizationCommonTestHelper

    protected val commonTestHelper: ClientOrganizationCommonTestHelper = ClientOrganizationCommonTestHelper()

}