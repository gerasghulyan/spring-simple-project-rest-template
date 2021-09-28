package com.vntana.core.service.catalog

import com.vntana.core.helper.integration.catalog.FacebookCatalogSettingIntegrationTestHelper
import com.vntana.core.helper.integration.organization.OrganizationIntegrationTestHelper
import com.vntana.core.helper.unit.catalog.FacebookCatalogSettingCommonTestHelper
import com.vntana.core.service.AbstractServiceIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 10:29 AM
 */
abstract class AbstractFacebookCatalogSettingServiceIntegrationTest : AbstractServiceIntegrationTest() {

    @Autowired
    protected lateinit var facebookCatalogSettingService: FacebookCatalogSettingService

    @Autowired
    protected lateinit var integrationTestHelper: FacebookCatalogSettingIntegrationTestHelper

    @Autowired
    protected lateinit var organizationIntegrationTestHelper: OrganizationIntegrationTestHelper

    protected val commonTestHelper: FacebookCatalogSettingCommonTestHelper = FacebookCatalogSettingCommonTestHelper()
}