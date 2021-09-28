package com.vntana.core.rest.resource.catalog

import com.vntana.core.helper.catalog.FacebookCatalogSettingResourceTestHelper
import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.rest.client.catalog.FacebookCatalogSettingResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Diana Gevorgyan.
 * Date: 9/27/2021
 * Time: 12:29 PM
 */
abstract class AbstractFacebookCatalogSettingWebTest : AbstractWebIntegrationTest() {

    @Autowired
    protected lateinit var facebookCatalogSettingResourceTestHelper: FacebookCatalogSettingResourceTestHelper

    @Autowired
    protected lateinit var organizationResourceTestHelper: OrganizationResourceTestHelper

    @Autowired
    protected lateinit var facebookCatalogSettingResourceClient: FacebookCatalogSettingResourceClient
}