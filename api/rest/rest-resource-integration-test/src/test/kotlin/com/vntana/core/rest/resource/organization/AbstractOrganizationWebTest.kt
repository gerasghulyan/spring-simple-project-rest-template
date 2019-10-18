package com.vntana.core.rest.resource.organization

import com.vntana.core.helper.rest.organization.OrganizationRestTestHelper
import com.vntana.core.rest.resource.AbstractWebIntegrationTest

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 5:51 PM
 */
abstract class AbstractOrganizationWebTest : AbstractWebIntegrationTest() {
    protected val restTestHelper = OrganizationRestTestHelper()

    override fun baseMapping(): String = "${targetUrl()}/organizations"
}