package com.vntana.core.rest.resource.organization

import com.vntana.core.helper.rest.organization.OrganizationRestTestHelper
import com.vntana.core.rest.client.organization.OrganizationResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 10/9/19
 * Time: 5:51 PM
 */
abstract class AbstractOrganizationWebTest : AbstractWebIntegrationTest() {
    protected val restTestHelper = OrganizationRestTestHelper()

    override fun baseMapping(): String = "${targetUrl()}/organizations"

    @Autowired
    protected lateinit var organizationResourceClient: OrganizationResourceClient
}