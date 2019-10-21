package com.vntana.core.rest.resource.client

import com.vntana.core.helper.rest.client.ClientOrganizationRestTestHelper
import com.vntana.core.rest.client.client.ClientOrganizationResourceClient
import com.vntana.core.rest.client.organization.OrganizationResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 5:51 PM
 */
abstract class AbstractClientOrganizationWebTest : AbstractWebIntegrationTest() {
    protected val restTestHelper = ClientOrganizationRestTestHelper()

    override fun baseMapping(): String = "${targetUrl()}/clients"

    @Autowired
    protected lateinit var clientOrganizationResourceClient: ClientOrganizationResourceClient

    @Autowired
    protected lateinit var organizationResourceClient: OrganizationResourceClient
}