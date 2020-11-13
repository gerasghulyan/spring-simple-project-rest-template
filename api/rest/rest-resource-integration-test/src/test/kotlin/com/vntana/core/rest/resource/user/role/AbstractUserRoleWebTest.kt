package com.vntana.core.rest.resource.user.role

import com.vntana.core.helper.client.ClientOrganizationResourceTestHelper
import com.vntana.core.helper.organization.OrganizationResourceTestHelper
import com.vntana.core.helper.token.auth.TokenAuthenticationResourceTestHelper
import com.vntana.core.helper.user.UserResourceTestHelper
import com.vntana.core.helper.user.role.UserRoleResourceTestHelper
import com.vntana.core.helper.user.role.UserRoleRestTestHelper
import com.vntana.core.rest.client.token.auth.TokenAuthenticationResourceClient
import com.vntana.core.rest.client.user.UserResourceClient
import com.vntana.core.rest.client.user.role.UserRoleResourceClient
import com.vntana.core.rest.resource.AbstractWebIntegrationTest
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arman Gevorgyan.
 * Date: 5/12/20
 * Time: 3:23 PM
 */
abstract class AbstractUserRoleWebTest : AbstractWebIntegrationTest() {

    @Autowired
    protected lateinit var userRoleResourceClient: UserRoleResourceClient

    @Autowired
    protected lateinit var userResourceClient: UserResourceClient

    @Autowired
    protected lateinit var tokenAuthenticationResourceClient: TokenAuthenticationResourceClient
    
    @Autowired
    protected lateinit var resourceTestHelper: UserRoleRestTestHelper

    @Autowired
    protected lateinit var organizationResourceTestHelper: OrganizationResourceTestHelper

    @Autowired
    protected lateinit var clientOrganizationResourceTestHelper: ClientOrganizationResourceTestHelper

    @Autowired
    protected lateinit var userResourceTestHelper: UserResourceTestHelper

    @Autowired
    protected lateinit var authTokenResourceTestHelper: TokenAuthenticationResourceTestHelper

    @Autowired
    protected lateinit var userRoleResourceTestHelper: UserRoleResourceTestHelper

}