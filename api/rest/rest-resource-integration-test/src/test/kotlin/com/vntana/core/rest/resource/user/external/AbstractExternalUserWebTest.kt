package com.vntana.core.rest.resource.user.external

import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.rest.client.user.external.ExternalUserResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Diana Gevorgyan
 * Date: 7/16/2021
 * Time: 3:01 PM
 */
abstract class AbstractExternalUserWebTest : AbstractWebIntegrationTest() {

    @Autowired
    lateinit var externalUserClient: ExternalUserResourceClient

    @Autowired
    protected lateinit var organizationResourceTestHelper: OrganizationResourceTestHelper
}