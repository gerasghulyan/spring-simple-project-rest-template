package com.vntana.core.rest.resource.client

import com.vntana.core.helper.rest.client.ClientOrganizationRestTestHelper
import com.vntana.core.rest.resource.AbstractWebIntegrationTest

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 5:51 PM
 */
abstract class AbstractClientOrganizationWebTest : AbstractWebIntegrationTest() {
    protected val restTestHelper = ClientOrganizationRestTestHelper()

    override fun baseMapping(): String = "${targetUrl()}/clients"
}