package com.vntana.core.rest.resource.token.auth

import com.vntana.core.helper.client.ClientOrganizationResourceTestHelper
import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.helper.token.auth.TokenAuthenticationResourceTestHelper
import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.rest.client.token.auth.TokenAuthenticationResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arman Gevorgyan.
 * Date: 3/23/20
 * Time: 10:56 AM
 */
abstract class AbstractTokenAuthenticationWebTest : AbstractWebIntegrationTest() {

    @Autowired
    protected lateinit var tokenAuthenticationResourceClient: TokenAuthenticationResourceClient

    @Autowired
    protected lateinit var resourceTestHelper: TokenAuthenticationResourceTestHelper

    @Autowired
    protected lateinit var userResourceTestHelper: UserResourceTestHelper

    @Autowired
    protected lateinit var organizationResourceTestHelper: OrganizationResourceTestHelper

    @Autowired
    protected lateinit var clientOrganizationResourceTestHelper: ClientOrganizationResourceTestHelper
}